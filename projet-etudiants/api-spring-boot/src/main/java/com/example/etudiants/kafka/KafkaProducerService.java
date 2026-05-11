package com.example.etudiants.kafka;

import com.example.etudiants.dto.EtudiantDTO;
import com.example.etudiants.kafka.event.EtudiantEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service Kafka producteur.
 * Publie des événements dans des topics Kafka après les opérations métier.
 *
 * Partie 5 - Q2 : Communication asynchrone via Kafka
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private static final String TOPIC_ETUDIANT_CREATED = "etudiant-created";

    private final KafkaTemplate<String, EtudiantEvent> kafkaTemplate;

    public void publishEtudiantCreated(EtudiantDTO etudiant) {
        EtudiantEvent event = EtudiantEvent.builder()
                .etudiantId(etudiant.getId())
                .nom(etudiant.getNom())
                .email(etudiant.getEmail())
                .timestamp(LocalDateTime.now())
                .build();

        kafkaTemplate.send(TOPIC_ETUDIANT_CREATED, event);
        log.info("📤 [KAFKA] Événement etudiant-created publié pour l'étudiant ID {}", etudiant.getId());
    }
}
