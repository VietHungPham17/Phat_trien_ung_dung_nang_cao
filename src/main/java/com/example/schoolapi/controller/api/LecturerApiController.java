package com.example.schoolapi.controller.api;

import com.example.schoolapi.dto.LecturerDto;
import com.example.schoolapi.service.LecturerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/lecturers")
public class LecturerApiController {

    private final LecturerService service;

    public LecturerApiController(LecturerService service) {
        this.service = service;
    }

    @GetMapping
    public List<LecturerDto> list(@RequestParam(defaultValue = "false") boolean includeInactive) {
        return service.findAll(includeInactive);
    }

    @GetMapping("/{id}")
    public LecturerDto get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<LecturerDto> create(@RequestBody LecturerDto.Request request) {
        LecturerDto created = service.create(request);
        return ResponseEntity.created(URI.create("/api/lecturers/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public LecturerDto update(@PathVariable Long id, @RequestBody LecturerDto.Request request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDelete(@PathVariable Long id) {
        service.softDelete(id);
    }

    @PatchMapping("/{id}/restore")
    public LecturerDto restore(@PathVariable Long id) {
        return service.restore(id);
    }
}
