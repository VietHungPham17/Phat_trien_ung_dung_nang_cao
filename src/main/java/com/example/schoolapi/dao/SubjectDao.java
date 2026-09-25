package com.example.schoolapi.dao;

import com.example.schoolapi.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectDao extends JpaRepository<Subject, Long> {
    List<Subject> findByActiveTrueOrderByIdAsc();
}
