package com.example.schoolapi.service;

import com.example.schoolapi.dto.SemesterDto;

import java.util.List;

public interface SemesterService {
    List<SemesterDto> findAll(boolean includeInactive);
    SemesterDto findById(Long id);
    SemesterDto create(SemesterDto.Request request);
    SemesterDto update(Long id, SemesterDto.Request request);
    void softDelete(Long id);
    SemesterDto restore(Long id);
}
