package com.example.schoolapi.controller.api;

import com.example.schoolapi.dto.SubjectDto;
import com.example.schoolapi.service.SubjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectApiController {

    private final SubjectService service;

    public SubjectApiController(SubjectService service) {
        this.service = service;
    }

    @GetMapping
    public List<SubjectDto> list(@RequestParam(defaultValue = "false") boolean includeInactive) {
        return service.findAll(includeInactive);
    }

    @GetMapping("/{id}")
    public SubjectDto get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<SubjectDto> create(@RequestBody SubjectDto.Request request) {
        SubjectDto created = service.create(request);
        return ResponseEntity.created(URI.create("/api/subjects/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public SubjectDto update(@PathVariable Long id, @RequestBody SubjectDto.Request request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDelete(@PathVariable Long id) {
        service.softDelete(id);
    }

    @PatchMapping("/{id}/restore")
    public SubjectDto restore(@PathVariable Long id) {
        return service.restore(id);
    }
}
