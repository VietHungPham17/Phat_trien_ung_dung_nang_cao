package com.example.schoolapi.dao;

import com.example.schoolapi.entity.EducationLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationLevelDao extends JpaRepository<EducationLevel, Long> {
    List<EducationLevel> findByActiveTrueOrderByIdAsc();
}
