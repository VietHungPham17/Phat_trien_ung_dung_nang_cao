package com.example.schoolapi.service;

import com.example.schoolapi.dto.SubjectGroupDto;

import java.util.List;

public interface SubjectGroupService {
    List<SubjectGroupDto> findAll(boolean includeInactive);
    SubjectGroupDto findById(Long id);
    SubjectGroupDto create(SubjectGroupDto.Request request);
    SubjectGroupDto update(Long id, SubjectGroupDto.Request request);
    void softDelete(Long id);
    SubjectGroupDto restore(Long id);
}
