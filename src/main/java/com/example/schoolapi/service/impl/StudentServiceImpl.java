package com.example.schoolapi.service.impl;

import com.example.schoolapi.dao.StudentDao;
import com.example.schoolapi.dto.StudentRequest;
import com.example.schoolapi.dto.StudentResponse;
import com.example.schoolapi.entity.Student;
import com.example.schoolapi.exception.NotFoundException;
import com.example.schoolapi.mapper.StudentMapper;
import com.example.schoolapi.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of StudentService.
 * Uses StudentDao for all operations including NamedQuery methods.
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao;
    private final StudentMapper studentMapper;

    public StudentServiceImpl(StudentDao studentDao, StudentMapper studentMapper) {
        this.studentDao = studentDao;
        this.studentMapper = studentMapper;
    }

    // ==================== Query Operations ====================

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponse> findAll(boolean includeInactive) {
        List<Student> students = includeInactive
                ? studentDao.findAll()
                : studentDao.findByActiveTrueOrderByIdAsc();
        return studentMapper.toResponseList(students);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponse findById(Long id) {
        Student student = findActiveStudentById(id);
        return studentMapper.toResponse(student);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponse> findByDepartment(String department) {
        // Uses NamedQuery from Student entity
        List<Student> students = studentDao.findByDepartmentOrderByIdAsc(department);
        return studentMapper.toResponseList(students);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponse> searchByName(String keyword) {
        // Uses NamedQuery from Student entity
        List<Student> students = studentDao.findByStudentNameContaining(keyword);
        return studentMapper.toResponseList(students);
    }

    @Override
    @Transactional(readOnly = true)
    public long countActive() {
        // Uses NamedQuery from Student entity
        return studentDao.countActiveStudents();
    }

    // ==================== Command Operations ====================

    @Override
    public StudentResponse create(StudentRequest request) {
        Student student = new Student(request.department(), request.studentName());
        Student saved = studentDao.save(student);
        return studentMapper.toResponse(saved);
    }

    @Override
    public StudentResponse update(Long id, StudentRequest request) {
        Student student = findActiveStudentById(id);
        student.setDepartment(request.department());
        student.setStudentName(request.studentName());
        return studentMapper.toResponse(student);
    }

    @Override
    public void softDelete(Long id) {
        Student student = findActiveStudentById(id);
        student.setActive(false);
    }

    @Override
    public StudentResponse restore(Long id) {
        Student student = studentDao.findById(id)
                .orElseThrow(() -> new NotFoundException("Student", "id", id));
        student.setActive(true);
        return studentMapper.toResponse(student);
    }

    // ==================== Helper Methods ====================

    /**
     * Find active student by ID or throw NotFoundException.
     */
    private Student findActiveStudentById(Long id) {
        return studentDao.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new NotFoundException("Student", "id", id));
    }
}
