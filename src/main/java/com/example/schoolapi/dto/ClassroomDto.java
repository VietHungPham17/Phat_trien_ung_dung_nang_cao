package com.example.schoolapi.dto;

public record ClassroomDto(
        Long id,
        String code,
        String building,
        Integer capacity,
        String description,
        Boolean active
) {
    public record Request(
            String code,
            String building,
            Integer capacity,
            String description
    ) {}
}
