package com.example.grading.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class NoteDTO {
    private Long id;

    @NotNull(message = "studentId obligatoire")
    private Long studentId;

    @NotBlank(message = "matiere obligatoire")
    private String matiere;

    @NotNull
    @DecimalMin(value = "0.0", message = "Note minimale 0")
    @DecimalMax(value = "20.0", message = "Note maximale 20")
    private Double valeur;
}
