package com.example.schoolapi.dao;

import com.example.schoolapi.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MajorDao extends JpaRepository<Major, Long> {
    List<Major> findByActiveTrueOrderByIdAsc();
}
