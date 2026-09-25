package com.example.schoolapi.dto;

public record AcademicYearDto(
        Long id,
        String name,
        String description,
        Integer startYear,
        Integer endYear,
        Boolean active
) {
    public record Request(
            String name,
            String description,
            Integer startYear,
            Integer endYear
    ) {}
}
