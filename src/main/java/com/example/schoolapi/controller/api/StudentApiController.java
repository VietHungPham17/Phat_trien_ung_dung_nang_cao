package com.example.schoolapi.controller.api;

import com.example.schoolapi.dto.StudentRequest;
import com.example.schoolapi.dto.StudentResponse;
import com.example.schoolapi.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * REST Controller for Student API endpoints.
 */
@RestController
@RequestMapping("/api/students")
public class StudentApiController {

    private final StudentService studentService;

    public StudentApiController(StudentService studentService) {
        this.studentService = studentService;
    }

    // ==================== Query Endpoints ====================

    /**
     * GET /api/students - List all students.
     */
    @GetMapping
    public List<StudentResponse> list(
            @RequestParam(defaultValue = "false") boolean includeInactive) {
        return studentService.findAll(includeInactive);
    }

    /**
     * GET /api/students/{id} - Get student by ID.
     */
    @GetMapping("/{id}")
    public StudentResponse get(@PathVariable Long id) {
        return studentService.findById(id);
    }

    /**
     * GET /api/students/search?department=... - Search by department.
     */
    @GetMapping("/search/department")
    public List<StudentResponse> searchByDepartment(@RequestParam String department) {
        return studentService.findByDepartment(department);
    }

    /**
     * GET /api/students/search?name=... - Search by name keyword.
     */
    @GetMapping("/search/name")
    public List<StudentResponse> searchByName(@RequestParam String name) {
        return studentService.searchByName(name);
    }

    /**
     * GET /api/students/count - Count active students.
     */
    @GetMapping("/count")
    public long countActive() {
        return studentService.countActive();
    }

    // ==================== Command Endpoints ====================

    /**
     * POST /api/students - Create new student.
     */
    @PostMapping
    public ResponseEntity<StudentResponse> create(@Valid @RequestBody StudentRequest request) {
        StudentResponse created = studentService.create(request);
        return ResponseEntity
                .created(URI.create("/api/students/" + created.id()))
                .body(created);
    }

    /**
     * PUT /api/students/{id} - Update existing student.
     */
    @PutMapping("/{id}")
    public StudentResponse update(@PathVariable Long id, @Valid @RequestBody StudentRequest request) {
        return studentService.update(id, request);
    }

    /**
     * DELETE /api/students/{id} - Soft delete student.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDelete(@PathVariable Long id) {
        studentService.softDelete(id);
    }

    /**
     * PATCH /api/students/{id}/restore - Restore soft-deleted student.
     */
    @PatchMapping("/{id}/restore")
    public StudentResponse restore(@PathVariable Long id) {
        return studentService.restore(id);
    }
}
