package com.example.schoolapi.dto;

public record SubjectDto(
        Long id,
        String code,
        String name,
        Integer credits,
        Long subjectGroupId,
        String description,
        Boolean active
) {
    public record Request(
            String code,
            String name,
            Integer credits,
            Long subjectGroupId,
            String description
    ) {}
}
