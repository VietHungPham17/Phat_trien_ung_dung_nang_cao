package com.example.schoolapi.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "class_sections")
public class ClassSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "section_code", nullable = false, length = 50)
    private String sectionCode;

    @Column(name = "subject_id")
    private Long subjectId;

    @Column(name = "semester_id")
    private Long semesterId;

    @Column(name = "lecturer_id")
    private Long lecturerId;

    @Column(name = "classroom_id")
    private Long classroomId;

    @Column(name = "max_students")
    private Integer maxStudents;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    protected ClassSection() {}

    public ClassSection(String sectionCode, Long subjectId, Long semesterId, Long lecturerId, Long classroomId, Integer maxStudents) {
        this.sectionCode = sectionCode;
        this.subjectId = subjectId;
        this.semesterId = semesterId;
        this.lecturerId = lecturerId;
        this.classroomId = classroomId;
        this.maxStudents = maxStudents;
        this.active = true;
    }

    public Long getId() { return id; }
    public String getSectionCode() { return sectionCode; }
    public void setSectionCode(String sectionCode) { this.sectionCode = sectionCode; }
    public Long getSubjectId() { return subjectId; }
    public void setSubjectId(Long subjectId) { this.subjectId = subjectId; }
    public Long getSemesterId() { return semesterId; }
    public void setSemesterId(Long semesterId) { this.semesterId = semesterId; }
    public Long getLecturerId() { return lecturerId; }
    public void setLecturerId(Long lecturerId) { this.lecturerId = lecturerId; }
    public Long getClassroomId() { return classroomId; }
    public void setClassroomId(Long classroomId) { this.classroomId = classroomId; }
    public Integer getMaxStudents() { return maxStudents; }
    public void setMaxStudents(Integer maxStudents) { this.maxStudents = maxStudents; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassSection that = (ClassSection) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
