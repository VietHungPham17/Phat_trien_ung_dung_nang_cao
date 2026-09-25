package com.example.schoolapi.dao;

import com.example.schoolapi.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO implementation for Student entity.
 * Implements custom query methods from StudentDaoCustom interface.
 * JPQL queries are defined HERE in this class.
 */
@Repository
public class StudentDaoImpl implements StudentDaoCustom {

    @PersistenceContext
    private EntityManager entityManager;

    // Setter for testing
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // ==================== Custom Query Methods ====================

    @Override
    public List<Student> findByDepartmentOrderByIdAsc(String department) {
        String jpql = "SELECT s FROM Student s WHERE s.department = :department ORDER BY s.id ASC";
        TypedQuery<Student> query = entityManager.createQuery(jpql, Student.class);
        query.setParameter("department", department);
        return query.getResultList();
    }

    @Override
    public List<Student> findByStudentNameContaining(String keyword) {
        String jpql = "SELECT s FROM Student s WHERE s.studentName LIKE %:keyword% ORDER BY s.id ASC";
        TypedQuery<Student> query = entityManager.createQuery(jpql, Student.class);
        query.setParameter("keyword", keyword);
        return query.getResultList();
    }

    @Override
    public long countActiveStudents() {
        String jpql = "SELECT COUNT(s) FROM Student s WHERE s.active = true";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        return query.getSingleResult();
    }

    @Override
    public List<Student> findActiveByDepartmentOrderByName(String department) {
        String jpql = "SELECT s FROM Student s WHERE s.department = :department AND s.active = true ORDER BY s.studentName ASC";
        TypedQuery<Student> query = entityManager.createQuery(jpql, Student.class);
        query.setParameter("department", department);
        return query.getResultList();
    }
}
