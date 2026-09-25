package com.example.schoolapi.service.impl;

import com.example.schoolapi.dao.CdrDao;
import com.example.schoolapi.dto.CdrDto;
import com.example.schoolapi.entity.Cdr;
import com.example.schoolapi.exception.NotFoundException;
import com.example.schoolapi.mapper.CdrMapper;
import com.example.schoolapi.service.CdrService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CdrServiceImpl implements CdrService {

    private final CdrDao dao;
    private final CdrMapper mapper;

    public CdrServiceImpl(CdrDao dao, CdrMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CdrDto> findAll(boolean includeInactive) {
        return mapper.toDtoList(includeInactive ? dao.findAll() : dao.findByActiveTrueOrderByIdAsc());
    }

    @Override
    @Transactional(readOnly = true)
    public CdrDto findById(Long id) {
        return mapper.toDto(findActiveById(id));
    }

    @Override
    public CdrDto create(CdrDto.Request request) {
        return mapper.toDto(dao.save(mapper.toEntity(request)));
    }

    @Override
    public CdrDto update(Long id, CdrDto.Request request) {
        Cdr e = findActiveById(id);
        e.setCode(request.code());
        e.setDescription(request.description());
        e.setMajorId(request.majorId());
        e.setLevel(request.level());
        return mapper.toDto(e);
    }

    @Override
    public void softDelete(Long id) {
        Cdr e = findActiveById(id);
        e.setActive(false);
    }

    @Override
    public CdrDto restore(Long id) {
        Cdr e = dao.findById(id).orElseThrow(() -> new NotFoundException("Cdr", "id", id));
        e.setActive(true);
        return mapper.toDto(e);
    }

    private Cdr findActiveById(Long id) {
        return dao.findById(id).filter(Cdr::getActive)
                .orElseThrow(() -> new NotFoundException("Cdr", "id", id));
    }
}
