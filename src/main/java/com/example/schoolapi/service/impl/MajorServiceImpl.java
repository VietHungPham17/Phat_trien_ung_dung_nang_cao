package com.example.schoolapi.service.impl;

import com.example.schoolapi.dao.MajorDao;
import com.example.schoolapi.dto.MajorDto;
import com.example.schoolapi.entity.Major;
import com.example.schoolapi.exception.NotFoundException;
import com.example.schoolapi.mapper.MajorMapper;
import com.example.schoolapi.service.MajorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MajorServiceImpl implements MajorService {

    private final MajorDao dao;
    private final MajorMapper mapper;

    public MajorServiceImpl(MajorDao dao, MajorMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MajorDto> findAll(boolean includeInactive) {
        return mapper.toDtoList(includeInactive ? dao.findAll() : dao.findByActiveTrueOrderByIdAsc());
    }

    @Override
    @Transactional(readOnly = true)
    public MajorDto findById(Long id) {
        return mapper.toDto(findActiveById(id));
    }

    @Override
    public MajorDto create(MajorDto.Request request) {
        return mapper.toDto(dao.save(mapper.toEntity(request)));
    }

    @Override
    public MajorDto update(Long id, MajorDto.Request request) {
        Major e = findActiveById(id);
        e.setName(request.name());
        e.setCode(request.code());
        e.setFacultyId(request.facultyId());
        e.setDescription(request.description());
        return mapper.toDto(e);
    }

    @Override
    public void softDelete(Long id) {
        Major e = findActiveById(id);
        e.setActive(false);
    }

    @Override
    public MajorDto restore(Long id) {
        Major e = dao.findById(id).orElseThrow(() -> new NotFoundException("Major", "id", id));
        e.setActive(true);
        return mapper.toDto(e);
    }

    private Major findActiveById(Long id) {
        return dao.findById(id).filter(Major::getActive)
                .orElseThrow(() -> new NotFoundException("Major", "id", id));
    }
}
