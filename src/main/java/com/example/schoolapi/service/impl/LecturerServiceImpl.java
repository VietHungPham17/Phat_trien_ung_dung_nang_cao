package com.example.schoolapi.service.impl;

import com.example.schoolapi.dao.LecturerDao;
import com.example.schoolapi.dto.LecturerDto;
import com.example.schoolapi.entity.Lecturer;
import com.example.schoolapi.exception.NotFoundException;
import com.example.schoolapi.mapper.LecturerMapper;
import com.example.schoolapi.service.LecturerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LecturerServiceImpl implements LecturerService {

    private final LecturerDao dao;
    private final LecturerMapper mapper;

    public LecturerServiceImpl(LecturerDao dao, LecturerMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LecturerDto> findAll(boolean includeInactive) {
        return mapper.toDtoList(includeInactive ? dao.findAll() : dao.findByActiveTrueOrderByIdAsc());
    }

    @Override
    @Transactional(readOnly = true)
    public LecturerDto findById(Long id) {
        return mapper.toDto(findActiveById(id));
    }

    @Override
    public LecturerDto create(LecturerDto.Request request) {
        return mapper.toDto(dao.save(mapper.toEntity(request)));
    }

    @Override
    public LecturerDto update(Long id, LecturerDto.Request request) {
        Lecturer e = findActiveById(id);
        e.setFullName(request.fullName());
        e.setEmail(request.email());
        e.setPhone(request.phone());
        e.setFacultyId(request.facultyId());
        e.setDegree(request.degree());
        return mapper.toDto(e);
    }

    @Override
    public void softDelete(Long id) {
        Lecturer e = findActiveById(id);
        e.setActive(false);
    }

    @Override
    public LecturerDto restore(Long id) {
        Lecturer e = dao.findById(id).orElseThrow(() -> new NotFoundException("Lecturer", "id", id));
        e.setActive(true);
        return mapper.toDto(e);
    }

    private Lecturer findActiveById(Long id) {
        return dao.findById(id).filter(Lecturer::getActive)
                .orElseThrow(() -> new NotFoundException("Lecturer", "id", id));
    }
}
