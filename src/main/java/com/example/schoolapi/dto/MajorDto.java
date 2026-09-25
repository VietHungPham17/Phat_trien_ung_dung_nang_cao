package com.example.schoolapi.dto;

public record MajorDto(
        Long id,
        String name,
        String code,
        Long facultyId,
        String description,
        Boolean active
) {
    public record Request(
            String name,
            String code,
            Long facultyId,
            String description
    ) {}
}
