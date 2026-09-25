package com.example.schoolapi.controller.api;

import com.example.schoolapi.dto.ClassSectionDto;
import com.example.schoolapi.service.ClassSectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/class-sections")
public class ClassSectionApiController {

    private final ClassSectionService service;

    public ClassSectionApiController(ClassSectionService service) {
        this.service = service;
    }

    @GetMapping
    public List<ClassSectionDto> list(@RequestParam(defaultValue = "false") boolean includeInactive) {
        return service.findAll(includeInactive);
    }

    @GetMapping("/{id}")
    public ClassSectionDto get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<ClassSectionDto> create(@RequestBody ClassSectionDto.Request request) {
        ClassSectionDto created = service.create(request);
        return ResponseEntity.created(URI.create("/api/class-sections/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public ClassSectionDto update(@PathVariable Long id, @RequestBody ClassSectionDto.Request request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDelete(@PathVariable Long id) {
        service.softDelete(id);
    }

    @PatchMapping("/{id}/restore")
    public ClassSectionDto restore(@PathVariable Long id) {
        return service.restore(id);
    }
}
