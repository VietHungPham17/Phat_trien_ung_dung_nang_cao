package com.example.schoolapi.service.impl;

import com.example.schoolapi.dao.FeedbackDao;
import com.example.schoolapi.dto.FeedbackDto;
import com.example.schoolapi.entity.Feedback;
import com.example.schoolapi.exception.NotFoundException;
import com.example.schoolapi.mapper.FeedbackMapper;
import com.example.schoolapi.service.FeedbackService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackDao dao;
    private final FeedbackMapper mapper;

    public FeedbackServiceImpl(FeedbackDao dao, FeedbackMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeedbackDto> findAll(boolean includeInactive) {
        return mapper.toDtoList(includeInactive ? dao.findAll() : dao.findByActiveTrueOrderByIdAsc());
    }

    @Override
    @Transactional(readOnly = true)
    public FeedbackDto findById(Long id) {
        return mapper.toDto(findActiveById(id));
    }

    @Override
    public FeedbackDto create(FeedbackDto.Request request) {
        return mapper.toDto(dao.save(mapper.toEntity(request)));
    }

    @Override
    public FeedbackDto update(Long id, FeedbackDto.Request request) {
        Feedback e = findActiveById(id);
        e.setSenderName(request.senderName());
        e.setContent(request.content());
        e.setTargetType(request.targetType());
        e.setTargetId(request.targetId());
        e.setCreatedAt(request.createdAt());
        return mapper.toDto(e);
    }

    @Override
    public void softDelete(Long id) {
        Feedback e = findActiveById(id);
        e.setActive(false);
    }

    @Override
    public FeedbackDto restore(Long id) {
        Feedback e = dao.findById(id).orElseThrow(() -> new NotFoundException("Feedback", "id", id));
        e.setActive(true);
        return mapper.toDto(e);
    }

    private Feedback findActiveById(Long id) {
        return dao.findById(id).filter(Feedback::getActive)
                .orElseThrow(() -> new NotFoundException("Feedback", "id", id));
    }
}
