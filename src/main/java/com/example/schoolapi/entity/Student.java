package com.example.schoolapi.entity;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * Student entity - maps to 'students' table in database.
 * Chỉ chứa định nghĩa entity, không chứa query.
 */
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "department", nullable = false, length = 100)
    private String department;

    @Column(name = "studentname", nullable = false, length = 150)
    private String studentName;

    @Column(name = "active", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean active = true;

    protected Student() {
    }

    public Student(String department, String studentName) {
        this.department = department;
        this.studentName = studentName;
        this.active = true;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", department='" + department + '\'' +
                ", studentName='" + studentName + '\'' +
                ", active=" + active +
                '}';
    }
}
