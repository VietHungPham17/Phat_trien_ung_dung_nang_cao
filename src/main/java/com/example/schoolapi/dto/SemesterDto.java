package com.example.schoolapi.dto;

public record SemesterDto(
        Long id,
        String name,
        Long academicYearId,
        String startDate,
        String endDate,
        Boolean active
) {
    public record Request(
            String name,
            Long academicYearId,
            String startDate,
            String endDate
    ) {}
}
