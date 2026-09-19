package com.example.schoolapi.student;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    /** GET /api/students?includeInactive=false */
    @GetMapping
    public List<StudentResponse> list(
            @RequestParam(defaultValue = "false") boolean includeInactive) {
        return service.findAll(includeInactive);
    }

    /** GET /api/students/{id} */
    @GetMapping("/{id}")
    public StudentResponse get(@PathVariable Long id) {
        return service.findById(id);
    }

    /** POST /api/students */
    @PostMapping
    public ResponseEntity<StudentResponse> create(@Valid @RequestBody StudentRequest request) {
        StudentResponse created = service.create(request);
        return ResponseEntity
                .created(URI.create("/api/students/" + created.id()))
                .body(created);
    }

    /** PUT /api/students/{id} */
    @PutMapping("/{id}")
    public StudentResponse update(@PathVariable Long id, @Valid @RequestBody StudentRequest request) {
        return service.update(id, request);
    }

    /** DELETE /api/students/{id} - soft delete, sets active = false */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDelete(@PathVariable Long id) {
        service.softDelete(id);
    }

    /** PATCH /api/students/{id}/restore - undo a soft delete */
    @PatchMapping("/{id}/restore")
    public StudentResponse restore(@PathVariable Long id) {
        return service.restore(id);
    }
}
