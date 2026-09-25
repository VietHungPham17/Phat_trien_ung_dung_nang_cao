package com.example.schoolapi.dao;

import com.example.schoolapi.entity.Student;

import java.util.List;

/**
 * Custom DAO interface for Student entity.
 * Declares custom query method signatures.
 */
public interface StudentDaoCustom {

    /**
     * Find students by department ordered by ID.
     */
    List<Student> findByDepartmentOrderByIdAsc(String department);

    /**
     * Find students by name containing keyword ordered by ID.
     */
    List<Student> findByStudentNameContaining(String keyword);

    /**
     * Count all active students.
     */
    long countActiveStudents();

    /**
     * Find active students by department ordered by name.
     */
    List<Student> findActiveByDepartmentOrderByName(String department);
}
