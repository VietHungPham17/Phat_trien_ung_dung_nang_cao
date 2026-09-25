package com.example.schoolapi.service;

import com.example.schoolapi.dto.MajorDto;

import java.util.List;

public interface MajorService {
    List<MajorDto> findAll(boolean includeInactive);
    MajorDto findById(Long id);
    MajorDto create(MajorDto.Request request);
    MajorDto update(Long id, MajorDto.Request request);
    void softDelete(Long id);
    MajorDto restore(Long id);
}
