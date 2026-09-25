package com.example.schoolapi.dao;

import com.example.schoolapi.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassroomDao extends JpaRepository<Classroom, Long> {
    List<Classroom> findByActiveTrueOrderByIdAsc();
}
