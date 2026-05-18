package com.example.etudiants.mapper;

import com.example.etudiants.dto.EtudiantDTO;
import com.example.etudiants.entity.Departement;
import com.example.etudiants.entity.Etudiant;
import org.springframework.stereotype.Component;

@Component
public class EtudiantMapper {

    public EtudiantDTO toDto(Etudiant entity) {
        if (entity == null) return null;
        return EtudiantDTO.builder()
                .id(entity.getId())
                .cin(entity.getCin())
                .nom(entity.getNom())
                .dateNaissance(entity.getDateNaissance())
                .email(entity.getEmail())
                .anneePremiereInscription(entity.getAnneePremiereInscription())
                .departementId(entity.getDepartement() != null ? entity.getDepartement().getId() : null)
                .departementNom(entity.getDepartement() != null ? entity.getDepartement().getNom() : null)
                .age(entity.age())
                .build();
    }

    public Etudiant toEntity(EtudiantDTO dto, Departement departement) {
        if (dto == null) return null;
        return Etudiant.builder()
                .id(dto.getId())
                .cin(dto.getCin())
                .nom(dto.getNom())
                .dateNaissance(dto.getDateNaissance())
                .email(dto.getEmail())
                .anneePremiereInscription(dto.getAnneePremiereInscription())
                .departement(departement)
                .build();
    }

    public void updateEntity(Etudiant entity, EtudiantDTO dto, Departement departement) {
        entity.setCin(dto.getCin());
        entity.setNom(dto.getNom());
        entity.setDateNaissance(dto.getDateNaissance());
        entity.setEmail(dto.getEmail());
        entity.setAnneePremiereInscription(dto.getAnneePremiereInscription());
        entity.setDepartement(departement);
    }
}
