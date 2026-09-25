package com.example.schoolapi.dao;

import com.example.schoolapi.entity.IndustryGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IndustryGroupDao extends JpaRepository<IndustryGroup, Long> {
    List<IndustryGroup> findByActiveTrueOrderByIdAsc();
}
