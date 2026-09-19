package com.example.schoolapi.student;

import com.example.schoolapi.common.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<StudentResponse> findAll(boolean includeInactive) {
        List<Student> found = includeInactive
                ? repository.findAll()
                : repository.findByActiveTrueOrderByIdAsc();
        return found.stream().map(StudentResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public StudentResponse findById(Long id) {
        return StudentResponse.from(requireActive(id));
    }

    public StudentResponse create(StudentRequest request) {
        Student saved = repository.save(new Student(request.department(), request.studentName()));
        return StudentResponse.from(saved);
    }

    public StudentResponse update(Long id, StudentRequest request) {
        Student student = requireActive(id);
        student.setDepartment(request.department());
        student.setStudentName(request.studentName());
        return StudentResponse.from(student);
    }

    /** Soft delete: flips active to false, the row stays in the table. */
    public void softDelete(Long id) {
        requireActive(id).setActive(false);
    }

    /** Undo a soft delete. */
    public StudentResponse restore(Long id) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student " + id + " not found"));
        student.setActive(true);
        return StudentResponse.from(student);
    }

    private Student requireActive(Long id) {
        return repository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new NotFoundException("Student " + id + " not found"));
    }
}
