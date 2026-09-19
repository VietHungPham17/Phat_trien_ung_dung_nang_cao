package com.example.schoolapi.student;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Payload for create (POST) and update (PUT). */
public record StudentRequest(

        @NotBlank(message = "department must not be blank")
        @Size(max = 100, message = "department must be at most 100 characters")
        String department,

        @NotBlank(message = "studentName must not be blank")
        @Size(max = 150, message = "studentName must be at most 150 characters")
        String studentName
) {
}
