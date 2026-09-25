package com.example.schoolapi.controller.api;

import com.example.schoolapi.dto.CurriculumFrameworkDto;
import com.example.schoolapi.service.CurriculumFrameworkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/curriculum-frameworks")
public class CurriculumFrameworkApiController {

    private final CurriculumFrameworkService service;

    public CurriculumFrameworkApiController(CurriculumFrameworkService service) {
        this.service = service;
    }

    @GetMapping
    public List<CurriculumFrameworkDto> list(@RequestParam(defaultValue = "false") boolean includeInactive) {
        return service.findAll(includeInactive);
    }

    @GetMapping("/{id}")
    public CurriculumFrameworkDto get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<CurriculumFrameworkDto> create(@RequestBody CurriculumFrameworkDto.Request request) {
        CurriculumFrameworkDto created = service.create(request);
        return ResponseEntity.created(URI.create("/api/curriculum-frameworks/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public CurriculumFrameworkDto update(@PathVariable Long id, @RequestBody CurriculumFrameworkDto.Request request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDelete(@PathVariable Long id) {
        service.softDelete(id);
    }

    @PatchMapping("/{id}/restore")
    public CurriculumFrameworkDto restore(@PathVariable Long id) {
        return service.restore(id);
    }
}
