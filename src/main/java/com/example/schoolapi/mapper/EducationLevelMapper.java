package com.example.schoolapi.mapper;

import com.example.schoolapi.dto.EducationLevelDto;
import com.example.schoolapi.entity.EducationLevel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EducationLevelMapper {
    public EducationLevelDto toDto(EducationLevel e) {
        if (e == null) return null;
        return new EducationLevelDto(e.getId(), e.getName(), e.getDescription(), e.getActive());
    }

    public List<EducationLevelDto> toDtoList(List<EducationLevel> entities) {
        return entities == null ? List.of() : entities.stream().map(this::toDto).toList();
    }

    public EducationLevel toEntity(EducationLevelDto.Request r) {
        if (r == null) return null;
        return new EducationLevel(r.name(), r.description());
    }
}
