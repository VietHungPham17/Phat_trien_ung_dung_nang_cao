package com.example.schoolapi.controller.api;

import com.example.schoolapi.dto.ClassroomDto;
import com.example.schoolapi.service.ClassroomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/classrooms")
public class ClassroomApiController {

    private final ClassroomService service;

    public ClassroomApiController(ClassroomService service) {
        this.service = service;
    }

    @GetMapping
    public List<ClassroomDto> list(@RequestParam(defaultValue = "false") boolean includeInactive) {
        return service.findAll(includeInactive);
    }

    @GetMapping("/{id}")
    public ClassroomDto get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<ClassroomDto> create(@RequestBody ClassroomDto.Request request) {
        ClassroomDto created = service.create(request);
        return ResponseEntity.created(URI.create("/api/classrooms/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public ClassroomDto update(@PathVariable Long id, @RequestBody ClassroomDto.Request request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDelete(@PathVariable Long id) {
        service.softDelete(id);
    }

    @PatchMapping("/{id}/restore")
    public ClassroomDto restore(@PathVariable Long id) {
        return service.restore(id);
    }
}
