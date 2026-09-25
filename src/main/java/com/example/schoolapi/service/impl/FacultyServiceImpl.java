package com.example.schoolapi.service.impl;

import com.example.schoolapi.dao.FacultyDao;
import com.example.schoolapi.dto.FacultyDto;
import com.example.schoolapi.entity.Faculty;
import com.example.schoolapi.exception.NotFoundException;
import com.example.schoolapi.mapper.FacultyMapper;
import com.example.schoolapi.service.FacultyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FacultyServiceImpl implements FacultyService {

    private final FacultyDao dao;
    private final FacultyMapper mapper;

    public FacultyServiceImpl(FacultyDao dao, FacultyMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FacultyDto> findAll(boolean includeInactive) {
        return mapper.toDtoList(includeInactive ? dao.findAll() : dao.findByActiveTrueOrderByIdAsc());
    }

    @Override
    @Transactional(readOnly = true)
    public FacultyDto findById(Long id) {
        return mapper.toDto(findActiveById(id));
    }

    @Override
    public FacultyDto create(FacultyDto.Request request) {
        return mapper.toDto(dao.save(mapper.toEntity(request)));
    }

    @Override
    public FacultyDto update(Long id, FacultyDto.Request request) {
        Faculty e = findActiveById(id);
        e.setName(request.name());
        e.setCode(request.code());
        e.setDescription(request.description());
        return mapper.toDto(e);
    }

    @Override
    public void softDelete(Long id) {
        Faculty e = findActiveById(id);
        e.setActive(false);
    }

    @Override
    public FacultyDto restore(Long id) {
        Faculty e = dao.findById(id).orElseThrow(() -> new NotFoundException("Faculty", "id", id));
        e.setActive(true);
        return mapper.toDto(e);
    }

    private Faculty findActiveById(Long id) {
        return dao.findById(id).filter(Faculty::getActive)
                .orElseThrow(() -> new NotFoundException("Faculty", "id", id));
    }
}
