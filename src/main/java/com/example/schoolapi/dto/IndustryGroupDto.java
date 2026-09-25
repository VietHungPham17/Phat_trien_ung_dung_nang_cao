package com.example.schoolapi.dto;

public record IndustryGroupDto(
        Long id,
        String name,
        String code,
        String description,
        Boolean active
) {
    public record Request(
            String name,
            String code,
            String description
    ) {}
}
