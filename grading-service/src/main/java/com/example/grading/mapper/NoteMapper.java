package com.example.grading.mapper;

import com.example.grading.dto.NoteDTO;
import com.example.grading.entity.Note;
import org.springframework.stereotype.Component;

@Component
public class NoteMapper {
    public NoteDTO toDto(Note e) {
        if (e == null) return null;
        return NoteDTO.builder()
                .id(e.getId()).studentId(e.getStudentId())
                .matiere(e.getMatiere()).valeur(e.getValeur()).build();
    }

    public Note toEntity(NoteDTO d) {
        if (d == null) return null;
        return Note.builder()
                .id(d.getId()).studentId(d.getStudentId())
                .matiere(d.getMatiere()).valeur(d.getValeur()).build();
    }
}
