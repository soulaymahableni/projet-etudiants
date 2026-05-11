package com.example.grading.controller;

import com.example.grading.dto.NoteDTO;
import com.example.grading.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
@Tag(name = "Notes", description = "Gestion des notes")

public class NoteController {

    private final NoteService service;

    @GetMapping
    @Operation(summary = "Liste des notes (filtrage optionnel par studentId)")
    public List<NoteDTO> findAll(@RequestParam(required = false) Long studentId) {
        return studentId == null ? service.findAll() : service.findByStudent(studentId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recuperer une note")
    public NoteDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creer une note")
    public NoteDTO create(@Valid @RequestBody NoteDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre a jour une note")
    public NoteDTO update(@PathVariable Long id, @Valid @RequestBody NoteDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une note")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
