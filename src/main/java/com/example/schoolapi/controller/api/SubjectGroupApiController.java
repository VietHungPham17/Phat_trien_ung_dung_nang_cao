package com.example.schoolapi.controller.api;

import com.example.schoolapi.dto.SubjectGroupDto;
import com.example.schoolapi.service.SubjectGroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/subject-groups")
public class SubjectGroupApiController {

    private final SubjectGroupService service;

    public SubjectGroupApiController(SubjectGroupService service) {
        this.service = service;
    }

    @GetMapping
    public List<SubjectGroupDto> list(@RequestParam(defaultValue = "false") boolean includeInactive) {
        return service.findAll(includeInactive);
    }

    @GetMapping("/{id}")
    public SubjectGroupDto get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<SubjectGroupDto> create(@RequestBody SubjectGroupDto.Request request) {
        SubjectGroupDto created = service.create(request);
        return ResponseEntity.created(URI.create("/api/subject-groups/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public SubjectGroupDto update(@PathVariable Long id, @RequestBody SubjectGroupDto.Request request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDelete(@PathVariable Long id) {
        service.softDelete(id);
    }

    @PatchMapping("/{id}/restore")
    public SubjectGroupDto restore(@PathVariable Long id) {
        return service.restore(id);
    }
}
