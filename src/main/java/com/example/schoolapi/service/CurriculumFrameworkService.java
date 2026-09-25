package com.example.schoolapi.service;

import com.example.schoolapi.dto.CurriculumFrameworkDto;

import java.util.List;

public interface CurriculumFrameworkService {
    List<CurriculumFrameworkDto> findAll(boolean includeInactive);
    CurriculumFrameworkDto findById(Long id);
    CurriculumFrameworkDto create(CurriculumFrameworkDto.Request request);
    CurriculumFrameworkDto update(Long id, CurriculumFrameworkDto.Request request);
    void softDelete(Long id);
    CurriculumFrameworkDto restore(Long id);
}
