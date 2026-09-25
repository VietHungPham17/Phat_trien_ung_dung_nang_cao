package com.example.schoolapi.dto;

public record FeedbackDto(
        Long id,
        String senderName,
        String content,
        String targetType,
        Long targetId,
        String createdAt,
        Boolean active
) {
    public record Request(
            String senderName,
            String content,
            String targetType,
            Long targetId,
            String createdAt
    ) {}
}
