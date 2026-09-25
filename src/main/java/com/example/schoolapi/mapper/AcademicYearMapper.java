package com.example.schoolapi.mapper;

import com.example.schoolapi.dto.AcademicYearDto;
import com.example.schoolapi.entity.AcademicYear;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AcademicYearMapper {
    public AcademicYearDto toDto(AcademicYear e) {
        if (e == null) return null;
        return new AcademicYearDto(e.getId(), e.getName(), e.getDescription(),
                e.getStartYear(), e.getEndYear(), e.getActive());
    }

    public List<AcademicYearDto> toDtoList(List<AcademicYear> entities) {
        return entities == null ? List.of() : entities.stream().map(this::toDto).toList();
    }

    public AcademicYear toEntity(AcademicYearDto.Request r) {
        if (r == null) return null;
        return new AcademicYear(r.name(), r.description(), r.startYear(), r.endYear());
    }
}
