package com.example.grading.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EtudiantSummaryDTO {
    private Long id;
    private String cin;
    private String nom;
}
