package com.example.schoolapi.mapper;

import com.example.schoolapi.dto.CdrDto;
import com.example.schoolapi.entity.Cdr;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CdrMapper {
    public CdrDto toDto(Cdr e) {
        if (e == null) return null;
        return new CdrDto(e.getId(), e.getCode(), e.getDescription(),
                e.getMajorId(), e.getLevel(), e.getActive());
    }

    public List<CdrDto> toDtoList(List<Cdr> entities) {
        return entities == null ? List.of() : entities.stream().map(this::toDto).toList();
    }

    public Cdr toEntity(CdrDto.Request r) {
        if (r == null) return null;
        return new Cdr(r.code(), r.description(), r.majorId(), r.level());
    }
}
