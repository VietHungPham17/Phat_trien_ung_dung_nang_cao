package com.example.schoolapi.dto;

public record CurriculumFrameworkDto(
        Long id,
        String name,
        String description,
        Integer totalCredits,
        Boolean active
) {
    public record Request(
            String name,
            String description,
            Integer totalCredits
    ) {}
}
