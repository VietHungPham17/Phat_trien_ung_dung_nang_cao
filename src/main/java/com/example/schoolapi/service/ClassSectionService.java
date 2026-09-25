package com.example.schoolapi.service;

import com.example.schoolapi.dto.ClassSectionDto;

import java.util.List;

public interface ClassSectionService {
    List<ClassSectionDto> findAll(boolean includeInactive);
    ClassSectionDto findById(Long id);
    ClassSectionDto create(ClassSectionDto.Request request);
    ClassSectionDto update(Long id, ClassSectionDto.Request request);
    void softDelete(Long id);
    ClassSectionDto restore(Long id);
}
