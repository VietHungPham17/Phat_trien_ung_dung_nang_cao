package com.example.schoolapi.controller.api;

import com.example.schoolapi.dto.MajorDto;
import com.example.schoolapi.service.MajorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/majors")
public class MajorApiController {

    private final MajorService service;

    public MajorApiController(MajorService service) {
        this.service = service;
    }

    @GetMapping
    public List<MajorDto> list(@RequestParam(defaultValue = "false") boolean includeInactive) {
        return service.findAll(includeInactive);
    }

    @GetMapping("/{id}")
    public MajorDto get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<MajorDto> create(@RequestBody MajorDto.Request request) {
        MajorDto created = service.create(request);
        return ResponseEntity.created(URI.create("/api/majors/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public MajorDto update(@PathVariable Long id, @RequestBody MajorDto.Request request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDelete(@PathVariable Long id) {
        service.softDelete(id);
    }

    @PatchMapping("/{id}/restore")
    public MajorDto restore(@PathVariable Long id) {
        return service.restore(id);
    }
}
