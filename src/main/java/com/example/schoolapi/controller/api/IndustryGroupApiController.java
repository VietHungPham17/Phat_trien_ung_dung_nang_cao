package com.example.schoolapi.controller.api;

import com.example.schoolapi.dto.IndustryGroupDto;
import com.example.schoolapi.service.IndustryGroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/industry-groups")
public class IndustryGroupApiController {

    private final IndustryGroupService service;

    public IndustryGroupApiController(IndustryGroupService service) {
        this.service = service;
    }

    @GetMapping
    public List<IndustryGroupDto> list(@RequestParam(defaultValue = "false") boolean includeInactive) {
        return service.findAll(includeInactive);
    }

    @GetMapping("/{id}")
    public IndustryGroupDto get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<IndustryGroupDto> create(@RequestBody IndustryGroupDto.Request request) {
        IndustryGroupDto created = service.create(request);
        return ResponseEntity.created(URI.create("/api/industry-groups/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public IndustryGroupDto update(@PathVariable Long id, @RequestBody IndustryGroupDto.Request request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDelete(@PathVariable Long id) {
        service.softDelete(id);
    }

    @PatchMapping("/{id}/restore")
    public IndustryGroupDto restore(@PathVariable Long id) {
        return service.restore(id);
    }
}
