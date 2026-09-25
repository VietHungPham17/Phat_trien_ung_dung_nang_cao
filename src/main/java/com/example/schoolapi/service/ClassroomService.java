package com.example.schoolapi.service;

import com.example.schoolapi.dto.ClassroomDto;

import java.util.List;

public interface ClassroomService {
    List<ClassroomDto> findAll(boolean includeInactive);
    ClassroomDto findById(Long id);
    ClassroomDto create(ClassroomDto.Request request);
    ClassroomDto update(Long id, ClassroomDto.Request request);
    void softDelete(Long id);
    ClassroomDto restore(Long id);
}
