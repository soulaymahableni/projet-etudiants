package com.example.notification.kafka.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Événement consommé depuis le topic Kafka "note-created".
 * Doit avoir les mêmes champs que la classe NoteEvent côté producteur (grading-service).
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