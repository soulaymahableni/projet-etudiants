package com.example.etudiants.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartementDTO {

    private Long id;

    @NotBlank(message = "Le nom du departement est obligatoire")
    @Size(max = 100)
    private String nom;
}
