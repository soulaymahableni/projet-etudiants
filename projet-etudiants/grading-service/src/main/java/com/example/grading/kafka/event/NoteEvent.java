package com.example.grading.kafka.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Événement publié dans le topic Kafka "note-created"
 * lorsqu'une nouvelle note est enregistrée.
 *
 * Cet événement est consommé par notification-service qui simule
 * une notification à l'étudiant.
 *
 * Partie 5 - Q2 : Communication asynchrone via Kafka
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteEvent {

    private Long noteId;

    private Long studentId;

    private String matiere;

    private Double valeur;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
}