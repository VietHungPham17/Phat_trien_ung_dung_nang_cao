package com.example.schoolapi.dao;

import com.example.schoolapi.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * DAO interface for Student entity.
 * Extends JpaRepository for standard CRUD operations.
 * Custom query methods are declared here, implemented in StudentDaoImpl.
 */
@Repository
public interface StudentDao extends JpaRepository<Student, Long>, StudentDaoCustom {

    // ==================== Derived Query Methods (Spring Data) ====================

    /**
     * Find all active students ordered by ID.
     */
    List<Student> findByActiveTrueOrderByIdAsc();

    /**
     * Find student by ID if active.
     */
    Optional<Student> findByIdAndActiveTrue(Long id);

    // ==================== Custom Query Methods (implemented in StudentDaoImpl) ====================
    // findByDepartmentOrderByIdAsc(String department)
    // findByStudentNameContaining(String keyword)
    // countActiveStudents()
    // findActiveByDepartmentOrderByName(String department)
}
