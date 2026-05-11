package com.example.etudiants.controller;

import com.example.etudiants.dto.EtudiantDTO;
import com.example.etudiants.service.EtudiantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/etudiants")
@RequiredArgsConstructor
@Tag(name = "Etudiants", description = "Gestion des etudiants")

public class EtudiantController {

    private final EtudiantService service;

    @GetMapping
    @Operation(summary = "Lister les etudiants",
               description = "Retourne tous les etudiants ou filtre par annee de premiere inscription / departement")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Liste retournee")})
    public List<EtudiantDTO> findAll(
            @RequestParam(value = "annee", required = false) Integer annee,
            @RequestParam(value = "departementId", required = false) Long departementId) {
        if (annee != null) return service.findByAnnee(annee);
        if (departementId != null) return service.findByDepartement(departementId);
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recuperer un etudiant par id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Etudiant trouve"),
            @ApiResponse(responseCode = "404", description = "Etudiant introuvable")
    })
    public EtudiantDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creer un etudiant")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Etudiant cree"),
            @ApiResponse(responseCode = "400", description = "Validation echouee")
    })
    public EtudiantDTO create(@Valid @RequestBody EtudiantDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre a jour un etudiant")
    public EtudiantDTO update(@PathVariable Long id, @Valid @RequestBody EtudiantDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un etudiant")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Etudiant supprime")})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
