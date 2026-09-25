package com.example.schoolapi.mapper;

import com.example.schoolapi.dto.LecturerDto;
import com.example.schoolapi.entity.Lecturer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LecturerMapper {
    public LecturerDto toDto(Lecturer e) {
        if (e == null) return null;
        return new LecturerDto(e.getId(), e.getFullName(), e.getEmail(), e.getPhone(),
                e.getFacultyId(), e.getDegree(), e.getActive());
    }

    public List<LecturerDto> toDtoList(List<Lecturer> entities) {
        return entities == null ? List.of() : entities.stream().map(this::toDto).toList();
    }

    public Lecturer toEntity(LecturerDto.Request r) {
        if (r == null) return null;
        return new Lecturer(r.fullName(), r.email(), r.phone(), r.facultyId(), r.degree());
    }
}
