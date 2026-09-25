package com.example.schoolapi.controller.api;

import com.example.schoolapi.dto.FeedbackDto;
import com.example.schoolapi.service.FeedbackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackApiController {

    private final FeedbackService service;

    public FeedbackApiController(FeedbackService service) {
        this.service = service;
    }

    @GetMapping
    public List<FeedbackDto> list(@RequestParam(defaultValue = "false") boolean includeInactive) {
        return service.findAll(includeInactive);
    }

    @GetMapping("/{id}")
    public FeedbackDto get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<FeedbackDto> create(@RequestBody FeedbackDto.Request request) {
        FeedbackDto created = service.create(request);
        return ResponseEntity.created(URI.create("/api/feedbacks/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public FeedbackDto update(@PathVariable Long id, @RequestBody FeedbackDto.Request request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDelete(@PathVariable Long id) {
        service.softDelete(id);
    }

    @PatchMapping("/{id}/restore")
    public FeedbackDto restore(@PathVariable Long id) {
        return service.restore(id);
    }
}
