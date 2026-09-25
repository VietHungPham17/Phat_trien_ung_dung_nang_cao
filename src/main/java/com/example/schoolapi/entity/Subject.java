package com.example.schoolapi.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, length = 50)
    private String code;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "credits")
    private Integer credits;

    @Column(name = "subject_group_id")
    private Long subjectGroupId;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    protected Subject() {}

    public Subject(String code, String name, Integer credits, Long subjectGroupId, String description) {
        this.code = code;
        this.name = name;
        this.credits = credits;
        this.subjectGroupId = subjectGroupId;
        this.description = description;
        this.active = true;
    }

    public Long getId() { return id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }
    public Long getSubjectGroupId() { return subjectGroupId; }
    public void setSubjectGroupId(Long subjectGroupId) { this.subjectGroupId = subjectGroupId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject that = (Subject) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
