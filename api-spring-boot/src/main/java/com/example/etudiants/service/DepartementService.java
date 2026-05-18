package com.example.etudiants.service;

import com.example.etudiants.dto.DepartementDTO;
import com.example.etudiants.entity.Departement;
import com.example.etudiants.exception.ResourceNotFoundException;
import com.example.etudiants.mapper.DepartementMapper;
import com.example.etudiants.repository.DepartementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartementService {

    private final DepartementRepository repository;
    private final DepartementMapper mapper;

    @Cacheable(value = "departements")
    @Transactional(readOnly = true)
    public List<DepartementDTO> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public DepartementDTO findById(Long id) {
        return repository.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Departement", id));
    }

    @CacheEvict(value = "departements", allEntries = true)
    public DepartementDTO create(DepartementDTO dto) {
        Departement entity = mapper.toEntity(dto);
        entity.setId(null);
        return mapper.toDto(repository.save(entity));
    }

    @CacheEvict(value = "departements", allEntries = true)
    public DepartementDTO update(Long id, DepartementDTO dto) {
        Departement existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Departement", id));
        existing.setNom(dto.getNom());
        return mapper.toDto(repository.save(existing));
    }

    @CacheEvict(value = "departements", allEntries = true)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Departement", id);
        }
        repository.deleteById(id);
    }
}
