package com.example.schoolapi.dao;

import com.example.schoolapi.entity.Cdr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CdrDao extends JpaRepository<Cdr, Long> {
    List<Cdr> findByActiveTrueOrderByIdAsc();
}
