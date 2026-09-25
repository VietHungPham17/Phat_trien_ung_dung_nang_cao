package com.example.schoolapi.service;

import com.example.schoolapi.dto.StudentRequest;
import com.example.schoolapi.dto.StudentResponse;

import java.util.List;

/**
 * Service interface for Student operations.
 */
public interface StudentService {

    // ==================== Query Operations ====================

    /**
     * Get all students (active only or all based on parameter).
     */
    List<StudentResponse> findAll(boolean includeInactive);

    /**
     * Get student by ID.
     */
    StudentResponse findById(Long id);

    /**
     * Search students by department.
     */
    List<StudentResponse> findByDepartment(String department);

    /**
     * Search students by name keyword.
     */
    List<StudentResponse> searchByName(String keyword);

    /**
     * Count active students.
     */
    long countActive();

    // ==================== Command Operations ====================

    /**
     * Create a new student.
     */
    StudentResponse create(StudentRequest request);

    /**
     * Update an existing student.
     */
    StudentResponse update(Long id, StudentRequest request);

    /**
     * Soft delete a student (set active = false).
     */
    void softDelete(Long id);

    /**
     * Restore a soft-deleted student.
     */
    StudentResponse restore(Long id);
}
