package com.example.schoolapi.service.impl;

import com.example.schoolapi.dao.ClassSectionDao;
import com.example.schoolapi.dto.ClassSectionDto;
import com.example.schoolapi.entity.ClassSection;
import com.example.schoolapi.exception.NotFoundException;
import com.example.schoolapi.mapper.ClassSectionMapper;
import com.example.schoolapi.service.ClassSectionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClassSectionServiceImpl implements ClassSectionService {

    private final ClassSectionDao dao;
    private final ClassSectionMapper mapper;

    public ClassSectionServiceImpl(ClassSectionDao dao, ClassSectionMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassSectionDto> findAll(boolean includeInactive) {
        return mapper.toDtoList(includeInactive ? dao.findAll() : dao.findByActiveTrueOrderByIdAsc());
    }

    @Override
    @Transactional(readOnly = true)
    public ClassSectionDto findById(Long id) {
        return mapper.toDto(findActiveById(id));
    }

    @Override
    public ClassSectionDto create(ClassSectionDto.Request request) {
        return mapper.toDto(dao.save(mapper.toEntity(request)));
    }

    @Override
    public ClassSectionDto update(Long id, ClassSectionDto.Request request) {
        ClassSection e = findActiveById(id);
        e.setSectionCode(request.sectionCode());
        e.setSubjectId(request.subjectId());
        e.setSemesterId(request.semesterId());
        e.setLecturerId(request.lecturerId());
        e.setClassroomId(request.classroomId());
        e.setMaxStudents(request.maxStudents());
        return mapper.toDto(e);
    }

    @Override
    public void softDelete(Long id) {
        ClassSection e = findActiveById(id);
        e.setActive(false);
    }

    @Override
    public ClassSectionDto restore(Long id) {
        ClassSection e = dao.findById(id).orElseThrow(() -> new NotFoundException("ClassSection", "id", id));
        e.setActive(true);
        return mapper.toDto(e);
    }

    private ClassSection findActiveById(Long id) {
        return dao.findById(id).filter(ClassSection::getActive)
                .orElseThrow(() -> new NotFoundException("ClassSection", "id", id));
    }
}
