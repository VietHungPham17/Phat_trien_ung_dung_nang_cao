package com.example.schoolapi.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cdrs")
public class Cdr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, length = 50)
    private String code;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "major_id")
    private Long majorId;

    @Column(name = "level", length = 50)
    private String level;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    protected Cdr() {}

    public Cdr(String code, String description, Long majorId, String level) {
        this.code = code;
        this.description = description;
        this.majorId = majorId;
        this.level = level;
        this.active = true;
    }

    public Long getId() { return id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Long getMajorId() { return majorId; }
    public void setMajorId(Long majorId) { this.majorId = majorId; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cdr that = (Cdr) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
