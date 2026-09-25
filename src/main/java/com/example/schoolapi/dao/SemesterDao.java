package com.example.schoolapi.dao;

import com.example.schoolapi.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SemesterDao extends JpaRepository<Semester, Long> {
    List<Semester> findByActiveTrueOrderByIdAsc();
}
