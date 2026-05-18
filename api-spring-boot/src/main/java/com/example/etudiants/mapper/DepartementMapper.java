package com.example.etudiants.mapper;

import com.example.etudiants.dto.DepartementDTO;
import com.example.etudiants.entity.Departement;
import org.springframework.stereotype.Component;

@Component
public class DepartementMapper {

    public DepartementDTO toDto(Departement entity) {
        if (entity == null) return null;
        return DepartementDTO.builder()
                .id(entity.getId())
                .nom(entity.getNom())
                .build();
    }

    public Departement toEntity(DepartementDTO dto) {
        if (dto == null) return null;
        return Departement.builder()
                .id(dto.getId())
                .nom(dto.getNom())
                .build();
    }
}
