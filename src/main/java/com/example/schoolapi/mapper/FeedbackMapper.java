package com.example.schoolapi.mapper;

import com.example.schoolapi.dto.FeedbackDto;
import com.example.schoolapi.entity.Feedback;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FeedbackMapper {
    public FeedbackDto toDto(Feedback e) {
        if (e == null) return null;
        return new FeedbackDto(e.getId(), e.getSenderName(), e.getContent(),
                e.getTargetType(), e.getTargetId(), e.getCreatedAt(), e.getActive());
    }

    public List<FeedbackDto> toDtoList(List<Feedback> entities) {
        return entities == null ? List.of() : entities.stream().map(this::toDto).toList();
    }

    public Feedback toEntity(FeedbackDto.Request r) {
        if (r == null) return null;
        return new Feedback(r.senderName(), r.content(), r.targetType(), r.targetId(), r.createdAt());
    }
}
