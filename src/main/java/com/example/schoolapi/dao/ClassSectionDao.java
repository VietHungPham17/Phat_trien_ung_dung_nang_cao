package com.example.schoolapi.dao;

import com.example.schoolapi.entity.ClassSection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassSectionDao extends JpaRepository<ClassSection, Long> {
    List<ClassSection> findByActiveTrueOrderByIdAsc();
}
