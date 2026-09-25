package com.example.schoolapi.mapper;

import com.example.schoolapi.dto.CurriculumFrameworkDto;
import com.example.schoolapi.entity.CurriculumFramework;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CurriculumFrameworkMapper {
    public CurriculumFrameworkDto toDto(CurriculumFramework e) {
        if (e == null) return null;
        return new CurriculumFrameworkDto(e.getId(), e.getName(), e.getDescription(),
                e.getTotalCredits(), e.getActive());
    }

    public List<CurriculumFrameworkDto> toDtoList(List<CurriculumFramework> entities) {
        return entities == null ? List.of() : entities.stream().map(this::toDto).toList();
    }

    public CurriculumFramework toEntity(CurriculumFrameworkDto.Request r) {
        if (r == null) return null;
        return new CurriculumFramework(r.name(), r.description(), r.totalCredits());
    }
}
