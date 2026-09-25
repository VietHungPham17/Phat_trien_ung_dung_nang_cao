package com.example.schoolapi.service;

import com.example.schoolapi.dto.CdrDto;

import java.util.List;

public interface CdrService {
    List<CdrDto> findAll(boolean includeInactive);
    CdrDto findById(Long id);
    CdrDto create(CdrDto.Request request);
    CdrDto update(Long id, CdrDto.Request request);
    void softDelete(Long id);
    CdrDto restore(Long id);
}
