package com.example.schoolapi.service.impl;

import com.example.schoolapi.dao.StudentDao;
import com.example.schoolapi.dto.StudentRequest;
import com.example.schoolapi.dto.StudentResponse;
import com.example.schoolapi.entity.Student;
import com.example.schoolapi.exception.NotFoundException;
import com.example.schoolapi.mapper.StudentMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentDao studentDao;

    @Mock
    private StudentMapper studentMapper;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student sampleStudent;
    private StudentResponse sampleResponse;

    @BeforeEach
    void setUp() {
        sampleStudent = new Student("Computer Science", "John Doe");
        sampleStudent.setActive(true);

        sampleResponse = new StudentResponse(
                1L, "Computer Science", "John Doe", true
        );
    }

    // ==================== Query Operations ====================

    @Nested
    @DisplayName("findAll")
    class FindAllTests {

        @Test
        @DisplayName("should return only active students when includeInactive=false")
        void findAll_IncludeInactiveFalse_ReturnsActiveOnly() {
            when(studentDao.findByActiveTrueOrderByIdAsc()).thenReturn(List.of(sampleStudent));
            when(studentMapper.toResponseList(List.of(sampleStudent))).thenReturn(List.of(sampleResponse));

            List<StudentResponse> result = studentService.findAll(false);

            assertEquals(1, result.size());
            verify(studentDao).findByActiveTrueOrderByIdAsc();
            verify(studentDao, never()).findAll();
        }

        @Test
        @DisplayName("should return all students when includeInactive=true")
        void findAll_IncludeInactiveTrue_ReturnsAll() {
            Student inactive = new Student("Math", "Jane");
            inactive.setActive(false);
            when(studentDao.findAll()).thenReturn(List.of(sampleStudent, inactive));
            when(studentMapper.toResponseList(any())).thenReturn(List.of(
                    new StudentResponse(1L, "Computer Science", "John Doe", true),
                    new StudentResponse(2L, "Math", "Jane", false)
            ));

            List<StudentResponse> result = studentService.findAll(true);

            assertEquals(2, result.size());
            verify(studentDao).findAll();
            verify(studentDao, never()).findByActiveTrueOrderByIdAsc();
        }

        @Test
        @DisplayName("should return empty list when no students")
        void findAll_NoStudents_ReturnsEmptyList() {
            when(studentDao.findByActiveTrueOrderByIdAsc()).thenReturn(List.of());
            when(studentMapper.toResponseList(any())).thenReturn(List.of());

            List<StudentResponse> result = studentService.findAll(false);

            assertTrue(result.isEmpty());
        }
    }

    @Nested
    @DisplayName("findById")
    class FindByIdTests {

        @Test
        @DisplayName("should return student when found and active")
        void findById_FoundActive_ReturnsStudent() {
            when(studentDao.findByIdAndActiveTrue(1L)).thenReturn(Optional.of(sampleStudent));
            when(studentMapper.toResponse(sampleStudent)).thenReturn(sampleResponse);

            StudentResponse result = studentService.findById(1L);

            assertEquals("John Doe", result.studentName());
        }

        @Test
        @DisplayName("should throw NotFoundException when not found")
        void findById_NotFound_ThrowsException() {
            when(studentDao.findByIdAndActiveTrue(999L)).thenReturn(Optional.empty());

            NotFoundException exception = assertThrows(NotFoundException.class,
                    () -> studentService.findById(999L));

            assertTrue(exception.getMessage().contains("Student"));
            assertTrue(exception.getMessage().contains("id"));
        }
    }

    @Nested
    @DisplayName("findByDepartment (NamedQuery)")
    class FindByDepartmentTests {

        @Test
        @DisplayName("should return students in department")
        void findByDepartment_Found_ReturnsStudents() {
            when(studentDao.findByDepartmentOrderByIdAsc("Mathematics"))
                    .thenReturn(List.of(sampleStudent));
            when(studentMapper.toResponseList(any())).thenReturn(List.of(sampleResponse));

            List<StudentResponse> result = studentService.findByDepartment("Mathematics");

            assertEquals(1, result.size());
            verify(studentDao).findByDepartmentOrderByIdAsc("Mathematics");
        }

        @Test
        @DisplayName("should return empty when department not found")
        void findByDepartment_NotFound_ReturnsEmpty() {
            when(studentDao.findByDepartmentOrderByIdAsc("Biology")).thenReturn(List.of());
            when(studentMapper.toResponseList(any())).thenReturn(List.of());

            List<StudentResponse> result = studentService.findByDepartment("Biology");

            assertTrue(result.isEmpty());
        }
    }

    @Nested
    @DisplayName("searchByName (NamedQuery)")
    class SearchByNameTests {

        @Test
        @DisplayName("should return students matching keyword")
        void searchByName_MatchFound_ReturnsStudents() {
            when(studentDao.findByStudentNameContaining("John"))
                    .thenReturn(List.of(sampleStudent));
            when(studentMapper.toResponseList(any())).thenReturn(List.of(sampleResponse));

            List<StudentResponse> result = studentService.searchByName("John");

            assertEquals(1, result.size());
            verify(studentDao).findByStudentNameContaining("John");
        }
    }

    @Nested
    @DisplayName("countActive (NamedQuery)")
    class CountActiveTests {

        @Test
        @DisplayName("should return count of active students")
        void countActive_ReturnsCorrectCount() {
            when(studentDao.countActiveStudents()).thenReturn(5L);

            long count = studentService.countActive();

            assertEquals(5L, count);
            verify(studentDao).countActiveStudents();
        }

        @Test
        @DisplayName("should return 0 when no active students")
        void countActive_NoActive_ReturnsZero() {
            when(studentDao.countActiveStudents()).thenReturn(0L);

            long count = studentService.countActive();

            assertEquals(0L, count);
        }
    }

    // ==================== Command Operations ====================

    @Nested
    @DisplayName("create")
    class CreateTests {

        @Test
        @DisplayName("should create and return student")
        void create_ValidRequest_ReturnsStudent() {
            StudentRequest request = new StudentRequest("Computer Science", "John Doe");
            when(studentDao.save(any(Student.class))).thenReturn(sampleStudent);
            when(studentMapper.toResponse(sampleStudent)).thenReturn(sampleResponse);

            StudentResponse result = studentService.create(request);

            assertEquals("Computer Science", result.department());
            assertEquals("John Doe", result.studentName());
            verify(studentDao).save(any(Student.class));
        }
    }

    @Nested
    @DisplayName("update")
    class UpdateTests {

        @Test
        @DisplayName("should update and return student")
        void update_ValidRequest_ReturnsUpdated() {
            when(studentDao.findByIdAndActiveTrue(1L)).thenReturn(Optional.of(sampleStudent));

            StudentRequest request = new StudentRequest("Data Science", "Jane Doe");
            StudentResponse updated = new StudentResponse(1L, "Data Science", "Jane Doe", true);
            when(studentMapper.toResponse(any())).thenReturn(updated);

            StudentResponse result = studentService.update(1L, request);

            assertEquals("Data Science", result.department());
            assertEquals("Jane Doe", result.studentName());
        }

        @Test
        @DisplayName("should throw NotFoundException when student not found")
        void update_NotFound_ThrowsException() {
            when(studentDao.findByIdAndActiveTrue(999L)).thenReturn(Optional.empty());

            StudentRequest request = new StudentRequest("Data Science", "Jane");
            assertThrows(NotFoundException.class,
                    () -> studentService.update(999L, request));
        }
    }

    @Nested
    @DisplayName("softDelete")
    class SoftDeleteTests {

        @Test
        @DisplayName("should set active to false")
        void softDelete_Active_SetsActiveFalse() {
            when(studentDao.findByIdAndActiveTrue(1L)).thenReturn(Optional.of(sampleStudent));

            studentService.softDelete(1L);

            assertFalse(sampleStudent.getActive());
        }

        @Test
        @DisplayName("should throw NotFoundException when student not found")
        void softDelete_NotFound_ThrowsException() {
            when(studentDao.findByIdAndActiveTrue(999L)).thenReturn(Optional.empty());

            assertThrows(NotFoundException.class,
                    () -> studentService.softDelete(999L));
        }
    }

    @Nested
    @DisplayName("restore")
    class RestoreTests {

        @Test
        @DisplayName("should set active to true")
        void restore_Inactive_SetsActiveTrue() {
            Student inactive = new Student("Math", "John");
            inactive.setActive(false);
            when(studentDao.findById(1L)).thenReturn(Optional.of(inactive));
            when(studentMapper.toResponse(any())).thenReturn(
                    new StudentResponse(1L, "Math", "John", true)
            );

            StudentResponse result = studentService.restore(1L);

            assertTrue(result.active());
            assertTrue(inactive.getActive());
        }

        @Test
        @DisplayName("should throw NotFoundException when student not found")
        void restore_NotFound_ThrowsException() {
            when(studentDao.findById(999L)).thenReturn(Optional.empty());

            assertThrows(NotFoundException.class,
                    () -> studentService.restore(999L));
        }

        @Test
        @DisplayName("should be idempotent - restore already active student")
        void restore_AlreadyActive_NoException() {
            when(studentDao.findById(1L)).thenReturn(Optional.of(sampleStudent));
            when(studentMapper.toResponse(any())).thenReturn(sampleResponse);

            StudentResponse result = studentService.restore(1L);

            assertTrue(result.active());
        }
    }

    // ==================== Full Workflow Test ====================

    @Test
    @DisplayName("full workflow: create -> findById -> update -> softDelete -> restore")
    void fullWorkflow_AllOperations() {
        // 1. Create
        StudentRequest createRequest = new StudentRequest("Computer Science", "Alice");
        when(studentDao.save(any(Student.class))).thenReturn(sampleStudent);
        when(studentMapper.toResponse(any())).thenReturn(sampleResponse);
        studentService.create(createRequest);

        // 2. FindById - uses findByIdAndActiveTrue
        when(studentDao.findByIdAndActiveTrue(1L)).thenReturn(Optional.of(sampleStudent));
        studentService.findById(1L);

        // 3. Update - uses findByIdAndActiveTrue
        StudentRequest updateRequest = new StudentRequest("Data Science", "Alice Updated");
        studentService.update(1L, updateRequest);

        // 4. SoftDelete - uses findByIdAndActiveTrue
        studentService.softDelete(1L);
        assertFalse(sampleStudent.getActive());

        // 5. Restore (uses findById, not findByIdAndActiveTrue)
        sampleStudent.setActive(false);
        StudentResponse restored = new StudentResponse(1L, "Data Science", "Alice Updated", true);
        when(studentDao.findById(1L)).thenReturn(Optional.of(sampleStudent));
        when(studentMapper.toResponse(any())).thenReturn(restored);
        StudentResponse result = studentService.restore(1L);
        assertTrue(result.active());

        verify(studentDao, times(1)).save(any());
        // findByIdAndActiveTrue called 3 times: findById, update, softDelete
        verify(studentDao, times(3)).findByIdAndActiveTrue(any());
        verify(studentDao, times(1)).findById(any());
    }
}
