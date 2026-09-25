package com.example.schoolapi.controller.api;

import com.example.schoolapi.dto.StudentRequest;
import com.example.schoolapi.dto.StudentResponse;
import com.example.schoolapi.exception.GlobalExceptionHandler;
import com.example.schoolapi.exception.NotFoundException;
import com.example.schoolapi.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentApiController studentApiController;

    private StudentResponse sampleResponse;

    @BeforeEach
    void setUp() {
        sampleResponse = new StudentResponse(1L, "Computer Science", "John Doe", true);
    }

    // ==================== MockMvc Tests ====================

    @Nested
    @DisplayName("GET /api/students")
    class ListStudents {

        @Test
        @DisplayName("should return list of active students")
        void list_ReturnsActiveStudents() throws Exception {
            when(studentService.findAll(false)).thenReturn(List.of(sampleResponse));

            MockMvc mockMvc = MockMvcBuilders.standaloneSetup(studentApiController).build();

            mockMvc.perform(get("/api/students"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].studentName").value("John Doe"));

            verify(studentService).findAll(false);
        }

        @Test
        @DisplayName("should return all students when includeInactive=true")
        void list_IncludeInactive_ReturnsAll() throws Exception {
            when(studentService.findAll(true)).thenReturn(List.of(sampleResponse));

            MockMvc mockMvc = MockMvcBuilders.standaloneSetup(studentApiController).build();

            mockMvc.perform(get("/api/students").param("includeInactive", "true"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray());

            verify(studentService).findAll(true);
        }
    }

    @Nested
    @DisplayName("GET /api/students/{id}")
    class GetStudentById {

        @Test
        @DisplayName("should return student when found")
        void get_Found_ReturnsStudent() throws Exception {
            when(studentService.findById(1L)).thenReturn(sampleResponse);

            MockMvc mockMvc = MockMvcBuilders.standaloneSetup(studentApiController).build();

            mockMvc.perform(get("/api/students/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.studentName").value("John Doe"));

            verify(studentService).findById(1L);
        }

        @Test
        @DisplayName("should return 404 when not found")
        void get_NotFound_Returns404() throws Exception {
            when(studentService.findById(999L)).thenThrow(new NotFoundException("Student not found"));

            MockMvc mockMvc = MockMvcBuilders.standaloneSetup(studentApiController)
                    .setControllerAdvice(new GlobalExceptionHandler())
                    .build();

            mockMvc.perform(get("/api/students/999"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("POST /api/students")
    class CreateStudent {

        @Test
        @DisplayName("should create student and return 201")
        void create_ValidRequest_ReturnsCreated() throws Exception {
            when(studentService.create(any(StudentRequest.class))).thenReturn(sampleResponse);

            MockMvc mockMvc = MockMvcBuilders.standaloneSetup(studentApiController).build();

            String requestBody = "{\"department\":\"Computer Science\",\"studentName\":\"John Doe\"}";

            mockMvc.perform(post("/api/students")
                            .contentType("application/json")
                            .content(requestBody))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", "/api/students/1"));

            verify(studentService).create(any(StudentRequest.class));
        }
    }

    @Nested
    @DisplayName("PUT /api/students/{id}")
    class UpdateStudent {

        @Test
        @DisplayName("should update student")
        void update_ValidRequest_ReturnsUpdated() throws Exception {
            StudentResponse updated = new StudentResponse(1L, "Data Science", "Jane", true);
            when(studentService.update(eq(1L), any(StudentRequest.class))).thenReturn(updated);

            MockMvc mockMvc = MockMvcBuilders.standaloneSetup(studentApiController).build();

            String requestBody = "{\"department\":\"Data Science\",\"studentName\":\"Jane\"}";

            mockMvc.perform(put("/api/students/1")
                            .contentType("application/json")
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.department").value("Data Science"));

            verify(studentService).update(eq(1L), any(StudentRequest.class));
        }
    }

    @Nested
    @DisplayName("DELETE /api/students/{id}")
    class SoftDeleteStudent {

        @Test
        @DisplayName("should soft delete student and return 204")
        void delete_ReturnsNoContent() throws Exception {
            doNothing().when(studentService).softDelete(1L);

            MockMvc mockMvc = MockMvcBuilders.standaloneSetup(studentApiController).build();

            mockMvc.perform(delete("/api/students/1"))
                    .andExpect(status().isNoContent());

            verify(studentService).softDelete(1L);
        }

        @Test
        @DisplayName("should return 404 when not found")
        void delete_NotFound_Returns404() throws Exception {
            doThrow(new NotFoundException("Student not found")).when(studentService).softDelete(999L);

            MockMvc mockMvc = MockMvcBuilders.standaloneSetup(studentApiController)
                    .setControllerAdvice(new GlobalExceptionHandler())
                    .build();

            mockMvc.perform(delete("/api/students/999"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("PATCH /api/students/{id}/restore")
    class RestoreStudent {

        @Test
        @DisplayName("should restore soft deleted student")
        void restore_ReturnsRestored() throws Exception {
            when(studentService.restore(1L)).thenReturn(sampleResponse);

            MockMvc mockMvc = MockMvcBuilders.standaloneSetup(studentApiController).build();

            mockMvc.perform(patch("/api/students/1/restore"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.active").value(true));

            verify(studentService).restore(1L);
        }
    }

    @Nested
    @DisplayName("GET /api/students/search/department")
    class SearchByDepartment {

        @Test
        @DisplayName("should search students by department")
        void searchByDepartment_ReturnsResults() throws Exception {
            when(studentService.findByDepartment("Mathematics")).thenReturn(List.of(sampleResponse));

            MockMvc mockMvc = MockMvcBuilders.standaloneSetup(studentApiController).build();

            mockMvc.perform(get("/api/students/search/department").param("department", "Mathematics"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray());

            verify(studentService).findByDepartment("Mathematics");
        }
    }

    @Nested
    @DisplayName("GET /api/students/count")
    class CountActive {

        @Test
        @DisplayName("should return count of active students")
        void count_ReturnsCount() throws Exception {
            when(studentService.countActive()).thenReturn(5L);

            MockMvc mockMvc = MockMvcBuilders.standaloneSetup(studentApiController).build();

            mockMvc.perform(get("/api/students/count"))
                    .andExpect(status().isOk());

            verify(studentService).countActive();
        }
    }

    // ==================== Direct Controller Tests ====================

    @Test
    @DisplayName("list - direct call returns list")
    void list_DirectCall_ReturnsList() {
        when(studentService.findAll(false)).thenReturn(List.of(sampleResponse));

        List<StudentResponse> result = studentApiController.list(false);

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("get - direct call returns student")
    void get_DirectCall_ReturnsStudent() {
        when(studentService.findById(1L)).thenReturn(sampleResponse);

        StudentResponse result = studentApiController.get(1L);

        assertEquals("John Doe", result.studentName());
    }

    @Test
    @DisplayName("create - direct call returns ResponseEntity")
    void create_DirectCall_ReturnsCreated() {
        StudentRequest request = new StudentRequest("Computer Science", "John Doe");
        when(studentService.create(request)).thenReturn(sampleResponse);

        ResponseEntity<StudentResponse> response = studentApiController.create(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(URI.create("/api/students/1"), response.getHeaders().getLocation());
    }

    @Test
    @DisplayName("softDelete - direct call executes without exception")
    void softDelete_DirectCall_NoException() {
        doNothing().when(studentService).softDelete(1L);

        assertDoesNotThrow(() -> studentApiController.softDelete(1L));
        verify(studentService).softDelete(1L);
    }

    @Test
    @DisplayName("restore - direct call returns restored student")
    void restore_DirectCall_ReturnsRestored() {
        when(studentService.restore(1L)).thenReturn(sampleResponse);

        StudentResponse result = studentApiController.restore(1L);

        assertTrue(result.active());
    }
}
