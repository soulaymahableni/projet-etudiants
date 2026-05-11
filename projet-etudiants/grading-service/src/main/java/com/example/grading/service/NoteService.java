package com.example.grading.service;

import com.example.grading.client.EtudiantClient;
import com.example.grading.dto.NoteDTO;
import com.example.grading.entity.Note;
import com.example.grading.exception.ResourceNotFoundException;
import com.example.grading.kafka.KafkaProducerService;  // ✅ NOUVEAU IMPORT
import com.example.grading.mapper.NoteMapper;
import com.example.grading.repository.NoteRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NoteService {

    private final NoteRepository repository;
    private final NoteMapper mapper;
    private final EtudiantClient etudiantClient;
    private final KafkaProducerService kafkaProducerService;  // ✅ NOUVEAU

    @Transactional(readOnly = true)
    public List<NoteDTO> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public NoteDTO findById(Long id) {
        return repository.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Note", id));
    }

    @Transactional(readOnly = true)
    public List<NoteDTO> findByStudent(Long studentId) {
        return repository.findByStudentId(studentId).stream().map(mapper::toDto).toList();
    }

    public NoteDTO create(NoteDTO dto) {
        verifyEtudiantExists(dto.getStudentId());
        Note entity = mapper.toEntity(dto);
        entity.setId(null);
        Note saved = repository.save(entity);
        NoteDTO result = mapper.toDto(saved);

        // ✅ Partie 5 - Q2 : publier l'événement Kafka après la création de la note
        // notification-service consommera cet événement et simulera une notification.
        // Si Kafka est down, la note reste sauvegardée (découplage).
        kafkaProducerService.publishNoteCreated(
                saved.getId(),
                saved.getStudentId(),
                saved.getMatiere(),
                saved.getValeur()
        );

        return result;
    }

    public NoteDTO update(Long id, NoteDTO dto) {
        Note existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note", id));
        verifyEtudiantExists(dto.getStudentId());
        existing.setStudentId(dto.getStudentId());
        existing.setMatiere(dto.getMatiere());
        existing.setValeur(dto.getValeur());
        return mapper.toDto(repository.save(existing));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Note", id);
        repository.deleteById(id);
    }

    private void verifyEtudiantExists(Long studentId) {
        try {
            etudiantClient.getEtudiant(studentId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Etudiant", studentId);
        } catch (FeignException e) {
            log.warn("Echec d'appel a etudiant-service : {}", e.getMessage());
            throw new IllegalStateException("Service etudiant indisponible", e);
        }
    }
}