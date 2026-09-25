package com.example.schoolapi.controller.api;

import com.example.schoolapi.dto.FacultyDto;
import com.example.schoolapi.service.FacultyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/faculties")
public class FacultyApiController {

    private final FacultyService service;

    public FacultyApiController(FacultyService service) {
        this.service = service;
    }

    @GetMapping
    public List<FacultyDto> list(@RequestParam(defaultValue = "false") boolean includeInactive) {
        return service.findAll(includeInactive);
    }

    @GetMapping("/{id}")
    public FacultyDto get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<FacultyDto> create(@RequestBody FacultyDto.Request request) {
        FacultyDto created = service.create(request);
        return ResponseEntity.created(URI.create("/api/faculties/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public FacultyDto update(@PathVariable Long id, @RequestBody FacultyDto.Request request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDelete(@PathVariable Long id) {
        service.softDelete(id);
    }

    @PatchMapping("/{id}/restore")
    public FacultyDto restore(@PathVariable Long id) {
        return service.restore(id);
    }
}
