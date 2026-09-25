package com.example.schoolapi.dao;

import com.example.schoolapi.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacultyDao extends JpaRepository<Faculty, Long> {
    List<Faculty> findByActiveTrueOrderByIdAsc();
}
