package com.example.schoolapi.mapper;

import com.example.schoolapi.dto.ClassroomDto;
import com.example.schoolapi.entity.Classroom;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClassroomMapper {
    public ClassroomDto toDto(Classroom e) {
        if (e == null) return null;
        return new ClassroomDto(e.getId(), e.getCode(), e.getBuilding(),
                e.getCapacity(), e.getDescription(), e.getActive());
    }

    public List<ClassroomDto> toDtoList(List<Classroom> entities) {
        return entities == null ? List.of() : entities.stream().map(this::toDto).toList();
    }

    public Classroom toEntity(ClassroomDto.Request r) {
        if (r == null) return null;
        return new Classroom(r.code(), r.building(), r.capacity(), r.description());
    }
}
