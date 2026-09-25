package com.example.schoolapi.service;

import com.example.schoolapi.dto.SubjectDto;

import java.util.List;

public interface SubjectService {
    List<SubjectDto> findAll(boolean includeInactive);
    SubjectDto findById(Long id);
    SubjectDto create(SubjectDto.Request request);
    SubjectDto update(Long id, SubjectDto.Request request);
    void softDelete(Long id);
    SubjectDto restore(Long id);
}
