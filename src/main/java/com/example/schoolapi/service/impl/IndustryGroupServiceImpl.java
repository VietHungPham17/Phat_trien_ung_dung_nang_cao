package com.example.schoolapi.service.impl;

import com.example.schoolapi.dao.IndustryGroupDao;
import com.example.schoolapi.dto.IndustryGroupDto;
import com.example.schoolapi.entity.IndustryGroup;
import com.example.schoolapi.exception.NotFoundException;
import com.example.schoolapi.mapper.IndustryGroupMapper;
import com.example.schoolapi.service.IndustryGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IndustryGroupServiceImpl implements IndustryGroupService {

    private final IndustryGroupDao dao;
    private final IndustryGroupMapper mapper;

    public IndustryGroupServiceImpl(IndustryGroupDao dao, IndustryGroupMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<IndustryGroupDto> findAll(boolean includeInactive) {
        return mapper.toDtoList(includeInactive ? dao.findAll() : dao.findByActiveTrueOrderByIdAsc());
    }

    @Override
    @Transactional(readOnly = true)
    public IndustryGroupDto findById(Long id) {
        return mapper.toDto(findActiveById(id));
    }

    @Override
    public IndustryGroupDto create(IndustryGroupDto.Request request) {
        return mapper.toDto(dao.save(mapper.toEntity(request)));
    }

    @Override
    public IndustryGroupDto update(Long id, IndustryGroupDto.Request request) {
        IndustryGroup e = findActiveById(id);
        e.setName(request.name());
        e.setCode(request.code());
        e.setDescription(request.description());
        return mapper.toDto(e);
    }

    @Override
    public void softDelete(Long id) {
        IndustryGroup e = findActiveById(id);
        e.setActive(false);
    }

    @Override
    public IndustryGroupDto restore(Long id) {
        IndustryGroup e = dao.findById(id).orElseThrow(() -> new NotFoundException("IndustryGroup", "id", id));
        e.setActive(true);
        return mapper.toDto(e);
    }

    private IndustryGroup findActiveById(Long id) {
        return dao.findById(id).filter(IndustryGroup::getActive)
                .orElseThrow(() -> new NotFoundException("IndustryGroup", "id", id));
    }
}
