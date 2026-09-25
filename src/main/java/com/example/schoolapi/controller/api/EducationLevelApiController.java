package com.example.schoolapi.controller.api;

import com.example.schoolapi.dto.EducationLevelDto;
import com.example.schoolapi.service.EducationLevelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/education-levels")
public class EducationLevelApiController {

    private final EducationLevelService service;

    public EducationLevelApiController(EducationLevelService service) {
        this.service = service;
    }

    @GetMapping
    public List<EducationLevelDto> list(@RequestParam(defaultValue = "false") boolean includeInactive) {
        return service.findAll(includeInactive);
    }

    @GetMapping("/{id}")
    public EducationLevelDto get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<EducationLevelDto> create(@RequestBody EducationLevelDto.Request request) {
        EducationLevelDto created = service.create(request);
        return ResponseEntity.created(URI.create("/api/education-levels/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public EducationLevelDto update(@PathVariable Long id, @RequestBody EducationLevelDto.Request request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDelete(@PathVariable Long id) {
        service.softDelete(id);
    }

    @PatchMapping("/{id}/restore")
    public EducationLevelDto restore(@PathVariable Long id) {
        return service.restore(id);
    }
}
