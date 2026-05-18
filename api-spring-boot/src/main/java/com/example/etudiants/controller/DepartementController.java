package com.example.etudiants.controller;

import com.example.etudiants.dto.DepartementDTO;
import com.example.etudiants.service.DepartementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departements")
@RequiredArgsConstructor
@Tag(name = "Departements", description = "Gestion des departements")
@CrossOrigin(origins = "*")
public class DepartementController {

    private final DepartementService service;

    @GetMapping
    @Operation(summary = "Lister les departements")
    public List<DepartementDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recuperer un departement par id")
    public DepartementDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creer un departement")
    public DepartementDTO create(@Valid @RequestBody DepartementDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre a jour un departement")
    public DepartementDTO update(@PathVariable Long id, @Valid @RequestBody DepartementDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un departement")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
