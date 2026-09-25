package com.example.schoolapi.service.impl;

import com.example.schoolapi.dao.AcademicYearDao;
import com.example.schoolapi.dto.AcademicYearDto;
import com.example.schoolapi.entity.AcademicYear;
import com.example.schoolapi.exception.NotFoundException;
import com.example.schoolapi.mapper.AcademicYearMapper;
import com.example.schoolapi.service.AcademicYearService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AcademicYearServiceImpl implements AcademicYearService {

    private final AcademicYearDao dao;
    private final AcademicYearMapper mapper;

    public AcademicYearServiceImpl(AcademicYearDao dao, AcademicYearMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AcademicYearDto> findAll(boolean includeInactive) {
        return mapper.toDtoList(includeInactive ? dao.findAll() : dao.findByActiveTrueOrderByIdAsc());
    }

    @Override
    @Transactional(readOnly = true)
    public AcademicYearDto findById(Long id) {
        return mapper.toDto(findActiveById(id));
    }

    @Override
    public AcademicYearDto create(AcademicYearDto.Request request) {
        return mapper.toDto(dao.save(mapper.toEntity(request)));
    }

    @Override
    public AcademicYearDto update(Long id, AcademicYearDto.Request request) {
        AcademicYear entity = findActiveById(id);
        entity.setName(request.name());
        entity.setDescription(request.description());
        entity.setStartYear(request.startYear());
        entity.setEndYear(request.endYear());
        return mapper.toDto(entity);
    }

    @Override
    public void softDelete(Long id) {
        AcademicYear entity = findActiveById(id);
        entity.setActive(false);
    }

    @Override
    public AcademicYearDto restore(Long id) {
        AcademicYear entity = dao.findById(id)
                .orElseThrow(() -> new NotFoundException("AcademicYear", "id", id));
        entity.setActive(true);
        return mapper.toDto(entity);
    }

    private AcademicYear findActiveById(Long id) {
        return dao.findById(id)
                .filter(AcademicYear::getActive)
                .orElseThrow(() -> new NotFoundException("AcademicYear", "id", id));
    }
}
