package com.example.schoolapi.controller.api;

import com.example.schoolapi.dto.SemesterDto;
import com.example.schoolapi.service.SemesterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/semesters")
public class SemesterApiController {

    private final SemesterService service;

    public SemesterApiController(SemesterService service) {
        this.service = service;
    }

    @GetMapping
    public List<SemesterDto> list(@RequestParam(defaultValue = "false") boolean includeInactive) {
        return service.findAll(includeInactive);
    }

    @GetMapping("/{id}")
    public SemesterDto get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<SemesterDto> create(@RequestBody SemesterDto.Request request) {
        SemesterDto created = service.create(request);
        return ResponseEntity.created(URI.create("/api/semesters/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public SemesterDto update(@PathVariable Long id, @RequestBody SemesterDto.Request request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDelete(@PathVariable Long id) {
        service.softDelete(id);
    }

    @PatchMapping("/{id}/restore")
    public SemesterDto restore(@PathVariable Long id) {
        return service.restore(id);
    }
}
