package com.example.schoolapi.service.impl;

import com.example.schoolapi.dao.CurriculumFrameworkDao;
import com.example.schoolapi.dto.CurriculumFrameworkDto;
import com.example.schoolapi.entity.CurriculumFramework;
import com.example.schoolapi.exception.NotFoundException;
import com.example.schoolapi.mapper.CurriculumFrameworkMapper;
import com.example.schoolapi.service.CurriculumFrameworkService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CurriculumFrameworkServiceImpl implements CurriculumFrameworkService {

    private final CurriculumFrameworkDao dao;
    private final CurriculumFrameworkMapper mapper;

    public CurriculumFrameworkServiceImpl(CurriculumFrameworkDao dao, CurriculumFrameworkMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CurriculumFrameworkDto> findAll(boolean includeInactive) {
        return mapper.toDtoList(includeInactive ? dao.findAll() : dao.findByActiveTrueOrderByIdAsc());
    }

    @Override
    @Transactional(readOnly = true)
    public CurriculumFrameworkDto findById(Long id) {
        return mapper.toDto(findActiveById(id));
    }

    @Override
    public CurriculumFrameworkDto create(CurriculumFrameworkDto.Request request) {
        return mapper.toDto(dao.save(mapper.toEntity(request)));
    }

    @Override
    public CurriculumFrameworkDto update(Long id, CurriculumFrameworkDto.Request request) {
        CurriculumFramework e = findActiveById(id);
        e.setName(request.name());
        e.setDescription(request.description());
        e.setTotalCredits(request.totalCredits());
        return mapper.toDto(e);
    }

    @Override
    public void softDelete(Long id) {
        CurriculumFramework e = findActiveById(id);
        e.setActive(false);
    }

    @Override
    public CurriculumFrameworkDto restore(Long id) {
        CurriculumFramework e = dao.findById(id)
                .orElseThrow(() -> new NotFoundException("CurriculumFramework", "id", id));
        e.setActive(true);
        return mapper.toDto(e);
    }

    private CurriculumFramework findActiveById(Long id) {
        return dao.findById(id).filter(CurriculumFramework::getActive)
                .orElseThrow(() -> new NotFoundException("CurriculumFramework", "id", id));
    }
}
