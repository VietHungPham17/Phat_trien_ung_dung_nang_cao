package com.example.schoolapi.mapper;

import com.example.schoolapi.dto.StudentResponse;
import com.example.schoolapi.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentMapperTest {

    private StudentMapper studentMapper;
    private Student student;

    @BeforeEach
    void setUp() {
        studentMapper = new StudentMapper();
        student = new Student("Computer Science", "John Doe");
        student.setActive(true);
    }

    @Nested
    @DisplayName("toResponse")
    class ToResponseTests {

        @Test
        @DisplayName("should convert student entity to response DTO")
        void toResponse_ValidStudent_ReturnsResponse() {
            StudentResponse response = studentMapper.toResponse(student);

            assertNotNull(response);
            assertEquals("Computer Science", response.department());
            assertEquals("John Doe", response.studentName());
            assertTrue(response.active());
        }

        @Test
        @DisplayName("should return null when student is null")
        void toResponse_NullStudent_ReturnsNull() {
            StudentResponse response = studentMapper.toResponse(null);

            assertNull(response);
        }

        @Test
        @DisplayName("should map all fields correctly")
        void toResponse_AllFields_MappedCorrectly() {
            student.setDepartment("Mathematics");
            student.setStudentName("Jane Doe");
            student.setActive(false);

            StudentResponse response = studentMapper.toResponse(student);

            assertEquals("Mathematics", response.department());
            assertEquals("Jane Doe", response.studentName());
            assertFalse(response.active());
        }
    }

    @Nested
    @DisplayName("toResponseList")
    class ToResponseListTests {

        @Test
        @DisplayName("should convert list of students to list of responses")
        void toResponseList_ValidList_ReturnsResponseList() {
            Student student1 = new Student("Math", "Alice");
            Student student2 = new Student("Science", "Bob");
            List<Student> students = Arrays.asList(student1, student2);

            List<StudentResponse> responses = studentMapper.toResponseList(students);

            assertEquals(2, responses.size());
            assertEquals("Alice", responses.get(0).studentName());
            assertEquals("Bob", responses.get(1).studentName());
        }

        @Test
        @DisplayName("should return empty list when input is null")
        void toResponseList_NullList_ReturnsEmptyList() {
            List<StudentResponse> responses = studentMapper.toResponseList(null);

            assertNotNull(responses);
            assertTrue(responses.isEmpty());
        }

        @Test
        @DisplayName("should return empty list when input is empty")
        void toResponseList_EmptyList_ReturnsEmptyList() {
            List<StudentResponse> responses = studentMapper.toResponseList(List.of());

            assertNotNull(responses);
            assertTrue(responses.isEmpty());
        }

        @Test
        @DisplayName("should handle single item list")
        void toResponseList_SingleItem_ReturnsSingleResponse() {
            List<StudentResponse> responses = studentMapper.toResponseList(List.of(student));

            assertEquals(1, responses.size());
            assertEquals("John Doe", responses.get(0).studentName());
        }
    }
}
