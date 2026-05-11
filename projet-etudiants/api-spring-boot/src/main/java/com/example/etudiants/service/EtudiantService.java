package com.example.etudiants.service;

import com.example.etudiants.dto.EtudiantDTO;
import com.example.etudiants.entity.Departement;
import com.example.etudiants.entity.Etudiant;
import com.example.etudiants.exception.ResourceNotFoundException;
import com.example.etudiants.kafka.KafkaProducerService;  // ✅ NOUVEAU IMPORT
import com.example.etudiants.mapper.EtudiantMapper;
import com.example.etudiants.repository.DepartementRepository;
import com.example.etudiants.repository.EtudiantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EtudiantService {

    private final EtudiantRepository etudiantRepository;
    private final DepartementRepository departementRepository;
    private final EtudiantMapper mapper;
    private final KafkaProducerService kafkaProducerService;  // ✅ NOUVEAU

    @Cacheable(value = "etudiants")
    @Transactional(readOnly = true)
    public List<EtudiantDTO> findAll() {
        return etudiantRepository.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public EtudiantDTO findById(Long id) {
        Etudiant e = etudiantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Etudiant", id));
        return mapper.toDto(e);
    }

    @Transactional(readOnly = true)
    public List<EtudiantDTO> findByAnnee(int annee) {
        return etudiantRepository.findByAnneePremiereInscription(annee)
                .stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<EtudiantDTO> findByDepartement(Long departementId) {
        return etudiantRepository.findByDepartementId(departementId)
                .stream().map(mapper::toDto).toList();
    }

    @CacheEvict(value = "etudiants", allEntries = true)
    public EtudiantDTO create(EtudiantDTO dto) {
        Departement dep = resolveDepartement(dto.getDepartementId());
        Etudiant entity = mapper.toEntity(dto, dep);
        entity.setId(null);
        Etudiant saved = etudiantRepository.save(entity);
        EtudiantDTO result = mapper.toDto(saved);

        // ✅ Partie 5 - Q2 : publier l'événement Kafka après la création
        // Le service ne connaît pas notification-service, il parle uniquement à Kafka.
        // Si Kafka est down, la création reste persistée (découplage fort).
        kafkaProducerService.publishEtudiantCreated(result);

        return result;
    }

    @CacheEvict(value = "etudiants", allEntries = true)
    public EtudiantDTO update(Long id, EtudiantDTO dto) {
        Etudiant existing = etudiantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Etudiant", id));
        Departement dep = resolveDepartement(dto.getDepartementId());
        mapper.updateEntity(existing, dto, dep);
        return mapper.toDto(etudiantRepository.save(existing));
    }

    @CacheEvict(value = "etudiants", allEntries = true)
    public void delete(Long id) {
        if (!etudiantRepository.existsById(id)) {
            throw new ResourceNotFoundException("Etudiant", id);
        }
        etudiantRepository.deleteById(id);
    }

    private Departement resolveDepartement(Long departementId) {
        if (departementId == null) return null;
        return departementRepository.findById(departementId)
                .orElseThrow(() -> new ResourceNotFoundException("Departement", departementId));
    }
}