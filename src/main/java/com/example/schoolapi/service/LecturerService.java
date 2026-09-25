package com.example.schoolapi.service;

import com.example.schoolapi.dto.LecturerDto;

import java.util.List;

public interface LecturerService {
    List<LecturerDto> findAll(boolean includeInactive);
    LecturerDto findById(Long id);
    LecturerDto create(LecturerDto.Request request);
    LecturerDto update(Long id, LecturerDto.Request request);
    void softDelete(Long id);
    LecturerDto restore(Long id);
}
