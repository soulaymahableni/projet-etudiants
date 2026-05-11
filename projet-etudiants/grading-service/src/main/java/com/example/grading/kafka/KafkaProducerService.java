package com.example.grading.kafka;

import com.example.grading.kafka.event.NoteEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service Kafka producteur de grading-service.
 * Publie des événements NoteEvent quand une note est enregistrée.
 *
 * Partie 5 - Q2 : Communication asynchrone via Kafka
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private static final String TOPIC_NOTE_CREATED = "note-created";

    private final KafkaTemplate<String, NoteEvent> kafkaTemplate;

    /**
     * Publie un événement NoteEvent sur le topic "note-created".
     * notification-service consomme ce topic pour simuler l'envoi
     * d'une notification.
     */
    public void publishNoteCreated(Long noteId, Long studentId, String matiere, Double valeur) {
        NoteEvent event = NoteEvent.builder()
                .noteId(noteId)
                .studentId(studentId)
                .matiere(matiere)
                .valeur(valeur)
                .timestamp(LocalDateTime.now())
                .build();

        kafkaTemplate.send(TOPIC_NOTE_CREATED, event);
        log.info("📤 [KAFKA] Événement note-created publié — étudiant ID {} | matière : {} | valeur : {}",
                studentId, matiere, valeur);
    }
}