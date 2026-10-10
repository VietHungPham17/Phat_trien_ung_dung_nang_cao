package com.example.schoolapi.dao;

import com.example.schoolapi.entity.FileRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRecordDao extends JpaRepository<FileRecord, Long> {
    
    Optional<FileRecord> findByPath(String path);
    
}
