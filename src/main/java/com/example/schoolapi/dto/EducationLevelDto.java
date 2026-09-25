package com.example.schoolapi.dto;

public record EducationLevelDto(
        Long id,
        String name,
        String description,
        Boolean active
) {
    public record Request(
            String name,
            String description
    ) {}
}
