package com.example.schoolapi.dao;

import com.example.schoolapi.entity.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LecturerDao extends JpaRepository<Lecturer, Long> {
    List<Lecturer> findByActiveTrueOrderByIdAsc();
}
