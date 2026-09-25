package com.example.schoolapi.dao;

import com.example.schoolapi.entity.CurriculumFramework;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurriculumFrameworkDao extends JpaRepository<CurriculumFramework, Long> {
    List<CurriculumFramework> findByActiveTrueOrderByIdAsc();
}
