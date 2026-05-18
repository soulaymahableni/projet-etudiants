package com.example.etudiants.unit;

import com.example.etudiants.dto.EtudiantDTO;
import com.example.etudiants.entity.Departement;
import com.example.etudiants.entity.Etudiant;
import com.example.etudiants.mapper.EtudiantMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class EtudiantMapperTest {

    private final EtudiantMapper mapper = new EtudiantMapper();

    @Test
    void toDto_mapsCorrectly() {
        Departement dep = Departement.builder().id(2L).nom("Math").build();
        Etudiant e = Etudiant.builder()
                .id(1L).cin("12345678").nom("Alice")
                .dateNaissance(LocalDate.of(2000, 1, 1))
                .email("a@b.com").anneePremiereInscription(2020)
                .departement(dep).build();

        EtudiantDTO dto = mapper.toDto(e);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getDepartementId()).isEqualTo(2L);
        assertThat(dto.getDepartementNom()).isEqualTo("Math");
        assertThat(dto.getAge()).isGreaterThan(0);
    }

    @Test
    void toDto_withNullDepartement_handlesGracefully() {
        Etudiant e = Etudiant.builder()
                .id(1L).cin("123").nom("X")
                .dateNaissance(LocalDate.now().minusYears(20)).build();

        EtudiantDTO dto = mapper.toDto(e);

        assertThat(dto.getDepartementId()).isNull();
        assertThat(dto.getDepartementNom()).isNull();
    }

    @Test
    void toDto_null_returnsNull() {
        assertThat(mapper.toDto(null)).isNull();
    }

    @Test
    void toEntity_mapsCorrectly() {
        EtudiantDTO dto = EtudiantDTO.builder()
                .cin("123").nom("Bob")
                .dateNaissance(LocalDate.of(2001, 5, 5))
                .anneePremiereInscription(2021).build();
        Departement dep = Departement.builder().id(1L).build();

        Etudiant entity = mapper.toEntity(dto, dep);

        assertThat(entity.getNom()).isEqualTo("Bob");
        assertThat(entity.getDepartement()).isEqualTo(dep);
    }

    @Test
    void age_returnsCorrectYears() {
        Etudiant e = Etudiant.builder()
                .dateNaissance(LocalDate.now().minusYears(25)).build();
        assertThat(e.age()).isEqualTo(25);
    }

    @Test
    void age_returnsZeroIfNullDate() {
        Etudiant e = Etudiant.builder().build();
        assertThat(e.age()).isZero();
    }
}
