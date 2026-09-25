package com.example.schoolapi.service.impl;

import com.example.schoolapi.dao.SubjectGroupDao;
import com.example.schoolapi.dto.SubjectGroupDto;
import com.example.schoolapi.entity.SubjectGroup;
import com.example.schoolapi.exception.NotFoundException;
import com.example.schoolapi.mapper.SubjectGroupMapper;
import com.example.schoolapi.service.SubjectGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SubjectGroupServiceImpl implements SubjectGroupService {

    private final SubjectGroupDao dao;
    private final SubjectGroupMapper mapper;

    public SubjectGroupServiceImpl(SubjectGroupDao dao, SubjectGroupMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectGroupDto> findAll(boolean includeInactive) {
        return mapper.toDtoList(includeInactive ? dao.findAll() : dao.findByActiveTrueOrderByIdAsc());
    }

    @Override
    @Transactional(readOnly = true)
    public SubjectGroupDto findById(Long id) {
        return mapper.toDto(findActiveById(id));
    }

    @Override
    public SubjectGroupDto create(SubjectGroupDto.Request request) {
        return mapper.toDto(dao.save(mapper.toEntity(request)));
    }

    @Override
    public SubjectGroupDto update(Long id, SubjectGroupDto.Request request) {
        SubjectGroup e = findActiveById(id);
        e.setName(request.name());
        e.setCode(request.code());
        e.setDescription(request.description());
        return mapper.toDto(e);
    }

    @Override
    public void softDelete(Long id) {
        SubjectGroup e = findActiveById(id);
        e.setActive(false);
    }

    @Override
    public SubjectGroupDto restore(Long id) {
        SubjectGroup e = dao.findById(id).orElseThrow(() -> new NotFoundException("SubjectGroup", "id", id));
        e.setActive(true);
        return mapper.toDto(e);
    }

    private SubjectGroup findActiveById(Long id) {
        return dao.findById(id).filter(SubjectGroup::getActive)
                .orElseThrow(() -> new NotFoundException("SubjectGroup", "id", id));
    }
}
