package com.example.schoolapi.mapper;

import com.example.schoolapi.dto.ClassSectionDto;
import com.example.schoolapi.entity.ClassSection;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClassSectionMapper {
    public ClassSectionDto toDto(ClassSection e) {
        if (e == null) return null;
        return new ClassSectionDto(e.getId(), e.getSectionCode(), e.getSubjectId(),
                e.getSemesterId(), e.getLecturerId(), e.getClassroomId(),
                e.getMaxStudents(), e.getActive());
    }

    public List<ClassSectionDto> toDtoList(List<ClassSection> entities) {
        return entities == null ? List.of() : entities.stream().map(this::toDto).toList();
    }

    public ClassSection toEntity(ClassSectionDto.Request r) {
        if (r == null) return null;
        return new ClassSection(r.sectionCode(), r.subjectId(), r.semesterId(),
                r.lecturerId(), r.classroomId(), r.maxStudents());
    }
}
