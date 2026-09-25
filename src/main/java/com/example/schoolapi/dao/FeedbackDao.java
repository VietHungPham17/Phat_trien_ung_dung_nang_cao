package com.example.schoolapi.dao;

import com.example.schoolapi.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackDao extends JpaRepository<Feedback, Long> {
    List<Feedback> findByActiveTrueOrderByIdAsc();
}
