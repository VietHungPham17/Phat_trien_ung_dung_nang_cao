package com.example.schoolapi.service;

import com.example.schoolapi.dto.AcademicYearDto;

import java.util.List;

public interface AcademicYearService {
    List<AcademicYearDto> findAll(boolean includeInactive);
    AcademicYearDto findById(Long id);
    AcademicYearDto create(AcademicYearDto.Request request);
    AcademicYearDto update(Long id, AcademicYearDto.Request request);
    void softDelete(Long id);
    AcademicYearDto restore(Long id);
}
