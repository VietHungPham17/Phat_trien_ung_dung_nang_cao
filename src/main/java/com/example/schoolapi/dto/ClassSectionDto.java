package com.example.schoolapi.dto;

public record ClassSectionDto(
        Long id,
        String sectionCode,
        Long subjectId,
        Long semesterId,
        Long lecturerId,
        Long classroomId,
        Integer maxStudents,
        Boolean active
) {
    public record Request(
            String sectionCode,
            Long subjectId,
            Long semesterId,
            Long lecturerId,
            Long classroomId,
            Integer maxStudents
    ) {}
}
