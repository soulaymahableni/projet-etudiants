package com.example.etudiants.kafka.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Événement publié dans le topic Kafka "etudiant-created"
 * lorsqu'un nouvel étudiant est inscrit.
 *
 * Cet événement est consommé par notification-service qui simule
 * l'envoi d'un email de bienvenue.
 *
 * Partie 5 - Q2 : Communication asynchrone via Kafka
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EtudiantEvent {

    private Long etudiantId;

    private String nom;

    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
}