package com.example.schoolapi.student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByActiveTrueOrderByIdAsc();

    Optional<Student> findByIdAndActiveTrue(Long id);
}
