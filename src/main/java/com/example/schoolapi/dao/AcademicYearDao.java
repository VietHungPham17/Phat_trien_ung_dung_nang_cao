package com.example.schoolapi.dao;

import com.example.schoolapi.entity.AcademicYear;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcademicYearDao extends JpaRepository<AcademicYear, Long> {
    List<AcademicYear> findByActiveTrueOrderByIdAsc();
}
