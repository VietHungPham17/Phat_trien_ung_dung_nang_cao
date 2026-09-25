package com.example.schoolapi.service;

import com.example.schoolapi.dto.EducationLevelDto;

import java.util.List;

public interface EducationLevelService {
    List<EducationLevelDto> findAll(boolean includeInactive);
    EducationLevelDto findById(Long id);
    EducationLevelDto create(EducationLevelDto.Request request);
    EducationLevelDto update(Long id, EducationLevelDto.Request request);
    void softDelete(Long id);
    EducationLevelDto restore(Long id);
}
