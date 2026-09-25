package com.example.schoolapi.service.impl;

import com.example.schoolapi.dao.SubjectDao;
import com.example.schoolapi.dto.SubjectDto;
import com.example.schoolapi.entity.Subject;
import com.example.schoolapi.exception.NotFoundException;
import com.example.schoolapi.mapper.SubjectMapper;
import com.example.schoolapi.service.SubjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SubjectServiceImpl implements SubjectService {

    private final SubjectDao dao;
    private final SubjectMapper mapper;

    public SubjectServiceImpl(SubjectDao dao, SubjectMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectDto> findAll(boolean includeInactive) {
        return mapper.toDtoList(includeInactive ? dao.findAll() : dao.findByActiveTrueOrderByIdAsc());
    }

    @Override
    @Transactional(readOnly = true)
    public SubjectDto findById(Long id) {
        return mapper.toDto(findActiveById(id));
    }

    @Override
    public SubjectDto create(SubjectDto.Request request) {
        return mapper.toDto(dao.save(mapper.toEntity(request)));
    }

    @Override
    public SubjectDto update(Long id, SubjectDto.Request request) {
        Subject e = findActiveById(id);
        e.setCode(request.code());
        e.setName(request.name());
        e.setCredits(request.credits());
        e.setSubjectGroupId(request.subjectGroupId());
        e.setDescription(request.description());
        return mapper.toDto(e);
    }

    @Override
    public void softDelete(Long id) {
        Subject e = findActiveById(id);
        e.setActive(false);
    }

    @Override
    public SubjectDto restore(Long id) {
        Subject e = dao.findById(id).orElseThrow(() -> new NotFoundException("Subject", "id", id));
        e.setActive(true);
        return mapper.toDto(e);
    }

    private Subject findActiveById(Long id) {
        return dao.findById(id).filter(Subject::getActive)
                .orElseThrow(() -> new NotFoundException("Subject", "id", id));
    }
}
