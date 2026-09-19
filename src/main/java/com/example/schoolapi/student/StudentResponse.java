package com.example.schoolapi.student;

public record StudentResponse(Long id, String department, String studentName, Boolean active) {

    static StudentResponse from(Student s) {
        return new StudentResponse(s.getId(), s.getDepartment(), s.getStudentName(), s.getActive());
    }
}
