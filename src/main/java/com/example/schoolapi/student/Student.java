package com.example.schoolapi.student;

import jakarta.persistence.*;

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

    /** Soft-delete flag. false = deleted, hidden from normal reads. */
    @Column(name = "active", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean active = true;

    protected Student() {
    }

    public Student(String department, String studentName) {
        this.department = department;
        this.studentName = studentName;
        this.active = true;
    }

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
}
