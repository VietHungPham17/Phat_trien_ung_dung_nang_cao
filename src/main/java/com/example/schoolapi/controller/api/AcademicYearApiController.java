package com.example.schoolapi.controller.api;

import com.example.schoolapi.dto.AcademicYearDto;
import com.example.schoolapi.service.AcademicYearService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/academic-years")
public class AcademicYearApiController {

    private final AcademicYearService service;

    public AcademicYearApiController(AcademicYearService service) {
        this.service = service;
    }

    @GetMapping
    public List<AcademicYearDto> list(@RequestParam(defaultValue = "false") boolean includeInactive) {
        return service.findAll(includeInactive);
    }

    @GetMapping("/{id}")
    public AcademicYearDto get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<AcademicYearDto> create(@RequestBody AcademicYearDto.Request request) {
        AcademicYearDto created = service.create(request);
        return ResponseEntity.created(URI.create("/api/academic-years/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public AcademicYearDto update(@PathVariable Long id, @RequestBody AcademicYearDto.Request request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDelete(@PathVariable Long id) {
        service.softDelete(id);
    }

    @PatchMapping("/{id}/restore")
    public AcademicYearDto restore(@PathVariable Long id) {
        return service.restore(id);
    }
}
