package com.example.schoolapi.controller.api;

import com.example.schoolapi.dto.CdrDto;
import com.example.schoolapi.service.CdrService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/cdrs")
public class CdrApiController {

    private final CdrService service;

    public CdrApiController(CdrService service) {
        this.service = service;
    }

    @GetMapping
    public List<CdrDto> list(@RequestParam(defaultValue = "false") boolean includeInactive) {
        return service.findAll(includeInactive);
    }

    @GetMapping("/{id}")
    public CdrDto get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<CdrDto> create(@RequestBody CdrDto.Request request) {
        CdrDto created = service.create(request);
        return ResponseEntity.created(URI.create("/api/cdrs/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public CdrDto update(@PathVariable Long id, @RequestBody CdrDto.Request request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDelete(@PathVariable Long id) {
        service.softDelete(id);
    }

    @PatchMapping("/{id}/restore")
    public CdrDto restore(@PathVariable Long id) {
        return service.restore(id);
    }
}
