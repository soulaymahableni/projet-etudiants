package com.example.etudiants.unit;

import com.example.etudiants.dto.EtudiantDTO;
import com.example.etudiants.entity.Departement;
import com.example.etudiants.entity.Etudiant;
import com.example.etudiants.exception.ResourceNotFoundException;
import com.example.etudiants.mapper.EtudiantMapper;
import com.example.etudiants.repository.DepartementRepository;
import com.example.etudiants.repository.EtudiantRepository;
import com.example.etudiants.service.EtudiantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EtudiantService - tests unitaires")
class EtudiantServiceTest {

    @Mock private EtudiantRepository etudiantRepository;
    @Mock private DepartementRepository departementRepository;
    @Mock private EtudiantMapper mapper;

    @InjectMocks private EtudiantService service;

    private Etudiant etudiant;
    private EtudiantDTO dto;
    private Departement departement;

    @BeforeEach
    void setUp() {
        departement = Departement.builder().id(1L).nom("Info").build();
        etudiant = Etudiant.builder()
                .id(1L).cin("12345678").nom("Alice")
                .dateNaissance(LocalDate.of(2002, 1, 1))
                .anneePremiereInscription(2021)
                .departement(departement).build();
        dto = EtudiantDTO.builder()
                .id(1L).cin("12345678").nom("Alice")
                .dateNaissance(LocalDate.of(2002, 1, 1))
                .anneePremiereInscription(2021)
                .departementId(1L).build();
    }

    @Test
    @DisplayName("findAll retourne la liste mappee")
    void findAll_returnsList() {
        when(etudiantRepository.findAll()).thenReturn(List.of(etudiant));
        when(mapper.toDto(etudiant)).thenReturn(dto);

        List<EtudiantDTO> result = service.findAll();

        assertThat(result).hasSize(1).first().isEqualTo(dto);
        verify(etudiantRepository).findAll();
    }

    @Test
    @DisplayName("findById retourne l'etudiant existant")
    void findById_existing() {
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));
        when(mapper.toDto(etudiant)).thenReturn(dto);

        EtudiantDTO result = service.findById(1L);

        assertThat(result).isEqualTo(dto);
    }

    @Test
    @DisplayName("findById leve ResourceNotFoundException si absent")
    void findById_notFound() {
        when(etudiantRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Etudiant");
    }

    @Test
    @DisplayName("findByAnnee filtre par annee")
    void findByAnnee_filters() {
        when(etudiantRepository.findByAnneePremiereInscription(2021))
                .thenReturn(List.of(etudiant));
        when(mapper.toDto(etudiant)).thenReturn(dto);

        List<EtudiantDTO> result = service.findByAnnee(2021);

        assertThat(result).hasSize(1);
        verify(etudiantRepository).findByAnneePremiereInscription(2021);
    }

    @Test
    @DisplayName("create persiste un nouvel etudiant")
    void create_savesEtudiant() {
        when(departementRepository.findById(1L)).thenReturn(Optional.of(departement));
        when(mapper.toEntity(dto, departement)).thenReturn(etudiant);
        when(etudiantRepository.save(any())).thenReturn(etudiant);
        when(mapper.toDto(etudiant)).thenReturn(dto);

        EtudiantDTO result = service.create(dto);

        assertThat(result).isEqualTo(dto);
        verify(etudiantRepository).save(any());
    }

    @Test
    @DisplayName("update modifie un etudiant existant")
    void update_modifies() {
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));
        when(departementRepository.findById(1L)).thenReturn(Optional.of(departement));
        when(etudiantRepository.save(any())).thenReturn(etudiant);
        when(mapper.toDto(etudiant)).thenReturn(dto);

        EtudiantDTO result = service.update(1L, dto);

        assertThat(result).isEqualTo(dto);
        verify(mapper).updateEntity(etudiant, dto, departement);
    }

    @Test
    @DisplayName("update leve ResourceNotFoundException si etudiant absent")
    void update_notFound() {
        when(etudiantRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99L, dto))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("delete supprime un etudiant existant")
    void delete_existing() {
        when(etudiantRepository.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(etudiantRepository).deleteById(1L);
    }

    @Test
    @DisplayName("delete leve ResourceNotFoundException si absent")
    void delete_notFound() {
        when(etudiantRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> service.delete(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("findByDepartement filtre par departement")
    void findByDepartement_filters() {
        when(etudiantRepository.findByDepartementId(1L)).thenReturn(List.of(etudiant));
        when(mapper.toDto(etudiant)).thenReturn(dto);

        List<EtudiantDTO> result = service.findByDepartement(1L);

        assertThat(result).hasSize(1);
    }
}
