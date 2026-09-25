package com.example.schoolapi.service.impl;

import com.example.schoolapi.dao.ClassroomDao;
import com.example.schoolapi.dto.ClassroomDto;
import com.example.schoolapi.entity.Classroom;
import com.example.schoolapi.exception.NotFoundException;
import com.example.schoolapi.mapper.ClassroomMapper;
import com.example.schoolapi.service.ClassroomService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomDao dao;
    private final ClassroomMapper mapper;

    public ClassroomServiceImpl(ClassroomDao dao, ClassroomMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomDto> findAll(boolean includeInactive) {
        return mapper.toDtoList(includeInactive ? dao.findAll() : dao.findByActiveTrueOrderByIdAsc());
    }

    @Override
    @Transactional(readOnly = true)
    public ClassroomDto findById(Long id) {
        return mapper.toDto(findActiveById(id));
    }

    @Override
    public ClassroomDto create(ClassroomDto.Request request) {
        return mapper.toDto(dao.save(mapper.toEntity(request)));
    }

    @Override
    public ClassroomDto update(Long id, ClassroomDto.Request request) {
        Classroom e = findActiveById(id);
        e.setCode(request.code());
        e.setBuilding(request.building());
        e.setCapacity(request.capacity());
        e.setDescription(request.description());
        return mapper.toDto(e);
    }

    @Override
    public void softDelete(Long id) {
        Classroom e = findActiveById(id);
        e.setActive(false);
    }

    @Override
    public ClassroomDto restore(Long id) {
        Classroom e = dao.findById(id).orElseThrow(() -> new NotFoundException("Classroom", "id", id));
        e.setActive(true);
        return mapper.toDto(e);
    }

    private Classroom findActiveById(Long id) {
        return dao.findById(id).filter(Classroom::getActive)
                .orElseThrow(() -> new NotFoundException("Classroom", "id", id));
    }
}
