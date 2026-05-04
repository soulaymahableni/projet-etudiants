package com.example.etudiants.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EtudiantDTO {

    private Long id;

    @NotBlank(message = "Le CIN est obligatoire")
    @Size(min = 4, max = 20, message = "Le CIN doit comporter entre 4 et 20 caracteres")
    private String cin;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 100)
    private String nom;

    @NotNull(message = "La date de naissance est obligatoire")
    @Past(message = "La date de naissance doit etre dans le passe")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateNaissance;

    @Email(message = "L'email doit etre valide")
    private String email;

    @Min(value = 1900, message = "Annee invalide")
    private int anneePremiereInscription;

    private Long departementId;
    private String departementNom;

    /** Champ calcule renvoye dans la reponse JSON. */
    private Integer age;
}
