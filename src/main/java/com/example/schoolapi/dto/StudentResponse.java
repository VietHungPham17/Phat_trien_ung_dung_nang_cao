package com.example.schoolapi.dto;

/**
 * DTO for student responses.
 */
public record StudentResponse(
        Long id,
        String department,
        String studentName,
        Boolean active
) {
}
