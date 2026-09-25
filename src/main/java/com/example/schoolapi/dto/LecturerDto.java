package com.example.schoolapi.dto;

public record LecturerDto(
        Long id,
        String fullName,
        String email,
        String phone,
        Long facultyId,
        String degree,
        Boolean active
) {
    public record Request(
            String fullName,
            String email,
            String phone,
            Long facultyId,
            String degree
    ) {}
}
