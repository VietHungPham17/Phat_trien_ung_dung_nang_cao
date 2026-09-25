package com.example.schoolapi.dto;

public record CdrDto(
        Long id,
        String code,
        String description,
        Long majorId,
        String level,
        Boolean active
) {
    public record Request(
            String code,
            String description,
            Long majorId,
            String level
    ) {}
}
