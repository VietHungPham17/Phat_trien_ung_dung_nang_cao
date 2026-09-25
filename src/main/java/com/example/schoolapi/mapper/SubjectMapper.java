package com.example.schoolapi.mapper;

import com.example.schoolapi.dto.SubjectDto;
import com.example.schoolapi.entity.Subject;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubjectMapper {
    public SubjectDto toDto(Subject e) {
        if (e == null) return null;
        return new SubjectDto(e.getId(), e.getCode(), e.getName(), e.getCredits(),
                e.getSubjectGroupId(), e.getDescription(), e.getActive());
    }

    public List<SubjectDto> toDtoList(List<Subject> entities) {
        return entities == null ? List.of() : entities.stream().map(this::toDto).toList();
    }

    public Subject toEntity(SubjectDto.Request r) {
        if (r == null) return null;
        return new Subject(r.code(), r.name(), r.credits(), r.subjectGroupId(), r.description());
    }
}
