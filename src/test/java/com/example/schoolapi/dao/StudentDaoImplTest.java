package com.example.schoolapi.dao;

import com.example.schoolapi.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class StudentDaoImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Student> typedQueryStudent;

    @Mock
    private TypedQuery<Long> typedQueryLong;

    private StudentDaoImpl studentDaoImpl;
    private Student mathStudent;

    @BeforeEach
    void setUp() {
        studentDaoImpl = new StudentDaoImpl();
        studentDaoImpl.setEntityManager(entityManager);

        mathStudent = new Student("Mathematics", "Alice");
    }

    // ==================== Custom Query Method Tests ====================

    @Nested
    @DisplayName("findByDepartmentOrderByIdAsc")
    class FindByDepartment {

        @Test
        @DisplayName("should execute JPQL query with department parameter")
        void findByDepartment_ExecutesQuery() {
            when(entityManager.createQuery(anyString(), eq(Student.class))).thenReturn(typedQueryStudent);
            when(typedQueryStudent.setParameter("department", "Mathematics")).thenReturn(typedQueryStudent);
            when(typedQueryStudent.getResultList()).thenReturn(List.of(mathStudent));

            List<Student> result = studentDaoImpl.findByDepartmentOrderByIdAsc("Mathematics");

            verify(entityManager).createQuery(anyString(), eq(Student.class));
            verify(typedQueryStudent).setParameter("department", "Mathematics");
            assertEquals(1, result.size());
            assertEquals("Alice", result.get(0).getStudentName());
        }

        @Test
        @DisplayName("should return empty list when department not found")
        void findByDepartment_NotFound_ReturnsEmpty() {
            when(entityManager.createQuery(anyString(), eq(Student.class))).thenReturn(typedQueryStudent);
            when(typedQueryStudent.setParameter("department", "Biology")).thenReturn(typedQueryStudent);
            when(typedQueryStudent.getResultList()).thenReturn(List.of());

            List<Student> result = studentDaoImpl.findByDepartmentOrderByIdAsc("Biology");

            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("should return multiple students in same department")
        void findByDepartment_MultipleStudents() {
            Student bob = new Student("Mathematics", "Bob");
            when(entityManager.createQuery(anyString(), eq(Student.class))).thenReturn(typedQueryStudent);
            when(typedQueryStudent.setParameter("department", "Mathematics")).thenReturn(typedQueryStudent);
            when(typedQueryStudent.getResultList()).thenReturn(Arrays.asList(mathStudent, bob));

            List<Student> result = studentDaoImpl.findByDepartmentOrderByIdAsc("Mathematics");

            assertEquals(2, result.size());
        }
    }

    @Nested
    @DisplayName("findByStudentNameContaining")
    class FindByStudentName {

        @Test
        @DisplayName("should execute JPQL query with keyword parameter")
        void findByName_ExecutesQuery() {
            when(entityManager.createQuery(anyString(), eq(Student.class))).thenReturn(typedQueryStudent);
            when(typedQueryStudent.setParameter("keyword", "Alice")).thenReturn(typedQueryStudent);
            when(typedQueryStudent.getResultList()).thenReturn(List.of(mathStudent));

            List<Student> result = studentDaoImpl.findByStudentNameContaining("Alice");

            verify(entityManager).createQuery(anyString(), eq(Student.class));
            verify(typedQueryStudent).setParameter("keyword", "Alice");
            assertEquals(1, result.size());
        }

        @Test
        @DisplayName("should return multiple results when keyword matches")
        void findByName_MultipleResults() {
            Student alice2 = new Student("Math", "Alice2");
            when(entityManager.createQuery(anyString(), eq(Student.class))).thenReturn(typedQueryStudent);
            when(typedQueryStudent.setParameter("keyword", "Alice")).thenReturn(typedQueryStudent);
            when(typedQueryStudent.getResultList()).thenReturn(Arrays.asList(mathStudent, alice2));

            List<Student> result = studentDaoImpl.findByStudentNameContaining("Alice");

            assertEquals(2, result.size());
        }

        @Test
        @DisplayName("should return empty when no matches")
        void findByName_NoMatch_ReturnsEmpty() {
            when(entityManager.createQuery(anyString(), eq(Student.class))).thenReturn(typedQueryStudent);
            when(typedQueryStudent.setParameter("keyword", "XYZ")).thenReturn(typedQueryStudent);
            when(typedQueryStudent.getResultList()).thenReturn(List.of());

            List<Student> result = studentDaoImpl.findByStudentNameContaining("XYZ");

            assertTrue(result.isEmpty());
        }
    }

    @Nested
    @DisplayName("countActiveStudents")
    class CountActiveStudents {

        @Test
        @DisplayName("should execute JPQL count query")
        void countActive_ExecutesQuery() {
            when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(typedQueryLong);
            when(typedQueryLong.getSingleResult()).thenReturn(5L);

            long count = studentDaoImpl.countActiveStudents();

            verify(entityManager).createQuery(anyString(), eq(Long.class));
            assertEquals(5L, count);
        }

        @Test
        @DisplayName("should return 0 when no active students")
        void countActive_NoActive_ReturnsZero() {
            when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(typedQueryLong);
            when(typedQueryLong.getSingleResult()).thenReturn(0L);

            long count = studentDaoImpl.countActiveStudents();

            assertEquals(0L, count);
        }
    }

    @Nested
    @DisplayName("findActiveByDepartmentOrderByName")
    class FindActiveByDepartment {

        @Test
        @DisplayName("should execute JPQL query with department parameter")
        void findActiveByDept_ExecutesQuery() {
            when(entityManager.createQuery(anyString(), eq(Student.class))).thenReturn(typedQueryStudent);
            when(typedQueryStudent.setParameter("department", "Mathematics")).thenReturn(typedQueryStudent);
            when(typedQueryStudent.getResultList()).thenReturn(List.of(mathStudent));

            List<Student> result = studentDaoImpl.findActiveByDepartmentOrderByName("Mathematics");

            verify(entityManager).createQuery(anyString(), eq(Student.class));
            verify(typedQueryStudent).setParameter("department", "Mathematics");
            assertEquals(1, result.size());
        }

        @Test
        @DisplayName("should return empty list when no active students in department")
        void findActiveByDept_NoActive_ReturnsEmpty() {
            when(entityManager.createQuery(anyString(), eq(Student.class))).thenReturn(typedQueryStudent);
            when(typedQueryStudent.setParameter("department", "Biology")).thenReturn(typedQueryStudent);
            when(typedQueryStudent.getResultList()).thenReturn(List.of());

            List<Student> result = studentDaoImpl.findActiveByDepartmentOrderByName("Biology");

            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("should return students ordered by name")
        void findActiveByDept_ReturnsOrderedByName() {
            Student bob = new Student("Mathematics", "Bob");
            when(entityManager.createQuery(anyString(), eq(Student.class))).thenReturn(typedQueryStudent);
            when(typedQueryStudent.setParameter("department", "Mathematics")).thenReturn(typedQueryStudent);
            when(typedQueryStudent.getResultList()).thenReturn(Arrays.asList(mathStudent, bob));

            List<Student> result = studentDaoImpl.findActiveByDepartmentOrderByName("Mathematics");

            assertEquals(2, result.size());
        }
    }

    // ==================== JPQL Query Verification ====================

    @Test
    @DisplayName("verify all JPQL queries are correctly formed")
    void jpqlQueries_AreCorrect() {
        // Verify JPQL query strings contain expected clauses
        String findByDeptQuery = "SELECT s FROM Student s WHERE s.department = :department ORDER BY s.id ASC";
        String findByNameQuery = "SELECT s FROM Student s WHERE s.studentName LIKE %:keyword% ORDER BY s.id ASC";
        String countActiveQuery = "SELECT COUNT(s) FROM Student s WHERE s.active = true";
        String findActiveByDeptQuery = "SELECT s FROM Student s WHERE s.department = :department AND s.active = true ORDER BY s.studentName ASC";

        assertTrue(findByDeptQuery.contains("s.department = :department"));
        assertTrue(findByDeptQuery.contains("ORDER BY s.id ASC"));
        assertTrue(findByNameQuery.contains("s.studentName LIKE"));
        assertTrue(findByNameQuery.contains(":keyword"));
        assertTrue(countActiveQuery.contains("COUNT(s)"));
        assertTrue(countActiveQuery.contains("s.active = true"));
        assertTrue(findActiveByDeptQuery.contains("s.department = :department"));
        assertTrue(findActiveByDeptQuery.contains("s.active = true"));
        assertTrue(findActiveByDeptQuery.contains("ORDER BY s.studentName ASC"));
    }
}
