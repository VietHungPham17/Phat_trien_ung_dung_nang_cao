package com.example.schoolapi.service.impl;

import com.example.schoolapi.dao.SemesterDao;
import com.example.schoolapi.dto.SemesterDto;
import com.example.schoolapi.entity.Semester;
import com.example.schoolapi.exception.NotFoundException;
import com.example.schoolapi.mapper.SemesterMapper;
import com.example.schoolapi.service.SemesterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SemesterServiceImpl implements SemesterService {

    private final SemesterDao dao;
    private final SemesterMapper mapper;

    public SemesterServiceImpl(SemesterDao dao, SemesterMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SemesterDto> findAll(boolean includeInactive) {
        return mapper.toDtoList(includeInactive ? dao.findAll() : dao.findByActiveTrueOrderByIdAsc());
    }

    @Override
    @Transactional(readOnly = true)
    public SemesterDto findById(Long id) {
        return mapper.toDto(findActiveById(id));
    }

    @Override
    public SemesterDto create(SemesterDto.Request request) {
        return mapper.toDto(dao.save(mapper.toEntity(request)));
    }

    @Override
    public SemesterDto update(Long id, SemesterDto.Request request) {
        Semester e = findActiveById(id);
        e.setName(request.name());
        e.setAcademicYearId(request.academicYearId());
        e.setStartDate(request.startDate());
        e.setEndDate(request.endDate());
        return mapper.toDto(e);
    }

    @Override
    public void softDelete(Long id) {
        Semester e = findActiveById(id);
        e.setActive(false);
    }

    @Override
    public SemesterDto restore(Long id) {
        Semester e = dao.findById(id).orElseThrow(() -> new NotFoundException("Semester", "id", id));
        e.setActive(true);
        return mapper.toDto(e);
    }

    private Semester findActiveById(Long id) {
        return dao.findById(id).filter(Semester::getActive)
                .orElseThrow(() -> new NotFoundException("Semester", "id", id));
    }
}
