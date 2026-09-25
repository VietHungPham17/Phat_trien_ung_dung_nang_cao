package com.example.schoolapi.mapper;

import com.example.schoolapi.dto.MajorDto;
import com.example.schoolapi.entity.Major;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MajorMapper {
    public MajorDto toDto(Major e) {
        if (e == null) return null;
        return new MajorDto(e.getId(), e.getName(), e.getCode(),
                e.getFacultyId(), e.getDescription(), e.getActive());
    }

    public List<MajorDto> toDtoList(List<Major> entities) {
        return entities == null ? List.of() : entities.stream().map(this::toDto).toList();
    }

    public Major toEntity(MajorDto.Request r) {
        if (r == null) return null;
        return new Major(r.name(), r.code(), r.facultyId(), r.description());
    }
}
