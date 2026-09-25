package com.example.schoolapi.mapper;

import com.example.schoolapi.dto.FacultyDto;
import com.example.schoolapi.entity.Faculty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FacultyMapper {
    public FacultyDto toDto(Faculty e) {
        if (e == null) return null;
        return new FacultyDto(e.getId(), e.getName(), e.getCode(), e.getDescription(), e.getActive());
    }

    public List<FacultyDto> toDtoList(List<Faculty> entities) {
        return entities == null ? List.of() : entities.stream().map(this::toDto).toList();
    }

    public Faculty toEntity(FacultyDto.Request r) {
        if (r == null) return null;
        return new Faculty(r.name(), r.code(), r.description());
    }
}
