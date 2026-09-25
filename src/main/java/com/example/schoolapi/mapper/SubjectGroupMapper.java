package com.example.schoolapi.mapper;

import com.example.schoolapi.dto.SubjectGroupDto;
import com.example.schoolapi.entity.SubjectGroup;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubjectGroupMapper {
    public SubjectGroupDto toDto(SubjectGroup e) {
        if (e == null) return null;
        return new SubjectGroupDto(e.getId(), e.getName(), e.getCode(), e.getDescription(), e.getActive());
    }

    public List<SubjectGroupDto> toDtoList(List<SubjectGroup> entities) {
        return entities == null ? List.of() : entities.stream().map(this::toDto).toList();
    }

    public SubjectGroup toEntity(SubjectGroupDto.Request r) {
        if (r == null) return null;
        return new SubjectGroup(r.name(), r.code(), r.description());
    }
}
