package com.example.schoolapi.mapper;

import com.example.schoolapi.dto.IndustryGroupDto;
import com.example.schoolapi.entity.IndustryGroup;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IndustryGroupMapper {
    public IndustryGroupDto toDto(IndustryGroup e) {
        if (e == null) return null;
        return new IndustryGroupDto(e.getId(), e.getName(), e.getCode(), e.getDescription(), e.getActive());
    }

    public List<IndustryGroupDto> toDtoList(List<IndustryGroup> entities) {
        return entities == null ? List.of() : entities.stream().map(this::toDto).toList();
    }

    public IndustryGroup toEntity(IndustryGroupDto.Request r) {
        if (r == null) return null;
        return new IndustryGroup(r.name(), r.code(), r.description());
    }
}
