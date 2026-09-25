package com.example.schoolapi.service.impl;

import com.example.schoolapi.dao.EducationLevelDao;
import com.example.schoolapi.dto.EducationLevelDto;
import com.example.schoolapi.entity.EducationLevel;
import com.example.schoolapi.exception.NotFoundException;
import com.example.schoolapi.mapper.EducationLevelMapper;
import com.example.schoolapi.service.EducationLevelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EducationLevelServiceImpl implements EducationLevelService {

    private final EducationLevelDao dao;
    private final EducationLevelMapper mapper;

    public EducationLevelServiceImpl(EducationLevelDao dao, EducationLevelMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EducationLevelDto> findAll(boolean includeInactive) {
        return mapper.toDtoList(includeInactive ? dao.findAll() : dao.findByActiveTrueOrderByIdAsc());
    }

    @Override
    @Transactional(readOnly = true)
    public EducationLevelDto findById(Long id) {
        return mapper.toDto(findActiveById(id));
    }

    @Override
    public EducationLevelDto create(EducationLevelDto.Request request) {
        return mapper.toDto(dao.save(mapper.toEntity(request)));
    }

    @Override
    public EducationLevelDto update(Long id, EducationLevelDto.Request request) {
        EducationLevel e = findActiveById(id);
        e.setName(request.name());
        e.setDescription(request.description());
        return mapper.toDto(e);
    }

    @Override
    public void softDelete(Long id) {
        EducationLevel e = findActiveById(id);
        e.setActive(false);
    }

    @Override
    public EducationLevelDto restore(Long id) {
        EducationLevel e = dao.findById(id).orElseThrow(() -> new NotFoundException("EducationLevel", "id", id));
        e.setActive(true);
        return mapper.toDto(e);
    }

    private EducationLevel findActiveById(Long id) {
        return dao.findById(id).filter(EducationLevel::getActive)
                .orElseThrow(() -> new NotFoundException("EducationLevel", "id", id));
    }
}
