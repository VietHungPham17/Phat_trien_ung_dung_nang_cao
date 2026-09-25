package com.example.schoolapi.mapper;

import com.example.schoolapi.dto.SemesterDto;
import com.example.schoolapi.entity.Semester;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SemesterMapper {
    public SemesterDto toDto(Semester e) {
        if (e == null) return null;
        return new SemesterDto(e.getId(), e.getName(), e.getAcademicYearId(),
                e.getStartDate(), e.getEndDate(), e.getActive());
    }

    public List<SemesterDto> toDtoList(List<Semester> entities) {
        return entities == null ? List.of() : entities.stream().map(this::toDto).toList();
    }

    public Semester toEntity(SemesterDto.Request r) {
        if (r == null) return null;
        return new Semester(r.name(), r.academicYearId(), r.startDate(), r.endDate());
    }
}
