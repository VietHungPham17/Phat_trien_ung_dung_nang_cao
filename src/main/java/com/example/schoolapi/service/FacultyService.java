package com.example.schoolapi.service;

import com.example.schoolapi.dto.FacultyDto;

import java.util.List;

public interface FacultyService {
    List<FacultyDto> findAll(boolean includeInactive);
    FacultyDto findById(Long id);
    FacultyDto create(FacultyDto.Request request);
    FacultyDto update(Long id, FacultyDto.Request request);
    void softDelete(Long id);
    FacultyDto restore(Long id);
}
