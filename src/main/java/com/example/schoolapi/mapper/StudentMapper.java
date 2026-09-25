package com.example.schoolapi.mapper;

import com.example.schoolapi.dto.StudentResponse;
import com.example.schoolapi.entity.Student;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Student entity and DTOs.
 */
@Component
public class StudentMapper {

    /**
     * Convert Student entity to StudentResponse DTO.
     */
    public StudentResponse toResponse(Student student) {
        if (student == null) {
            return null;
        }
        return new StudentResponse(
                student.getId(),
                student.getDepartment(),
                student.getStudentName(),
                student.getActive()
        );
    }

    /**
     * Convert list of Student entities to list of StudentResponse DTOs.
     */
    public java.util.List<StudentResponse> toResponseList(java.util.List<Student> students) {
        if (students == null) {
            return java.util.List.of();
        }
        return students.stream()
                .map(this::toResponse)
                .toList();
    }
}
