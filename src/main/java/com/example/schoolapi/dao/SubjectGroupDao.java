package com.example.schoolapi.dao;

import com.example.schoolapi.entity.SubjectGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectGroupDao extends JpaRepository<SubjectGroup, Long> {
    List<SubjectGroup> findByActiveTrueOrderByIdAsc();
}
