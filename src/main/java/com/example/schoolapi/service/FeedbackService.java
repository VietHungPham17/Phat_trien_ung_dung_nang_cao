package com.example.schoolapi.service;

import com.example.schoolapi.dto.FeedbackDto;

import java.util.List;

public interface FeedbackService {
    List<FeedbackDto> findAll(boolean includeInactive);
    FeedbackDto findById(Long id);
    FeedbackDto create(FeedbackDto.Request request);
    FeedbackDto update(Long id, FeedbackDto.Request request);
    void softDelete(Long id);
    FeedbackDto restore(Long id);
}
