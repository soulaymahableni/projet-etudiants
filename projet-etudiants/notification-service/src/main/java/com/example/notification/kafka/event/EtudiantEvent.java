package com.example.notification.kafka.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Événement consommé depuis le topic Kafka "etudiant-created".
 * Doit avoir les mêmes champs que la classe EtudiantEvent côté producteur
 * (api-spring-boot/etudiant-service) pour que la désérialisation JSON fonctionne.
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