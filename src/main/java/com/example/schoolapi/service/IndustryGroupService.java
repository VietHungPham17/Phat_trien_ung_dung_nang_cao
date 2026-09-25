package com.example.schoolapi.service;

import com.example.schoolapi.dto.IndustryGroupDto;

import java.util.List;

public interface IndustryGroupService {
    List<IndustryGroupDto> findAll(boolean includeInactive);
    IndustryGroupDto findById(Long id);
    IndustryGroupDto create(IndustryGroupDto.Request request);
    IndustryGroupDto update(Long id, IndustryGroupDto.Request request);
    void softDelete(Long id);
    IndustryGroupDto restore(Long id);
}
