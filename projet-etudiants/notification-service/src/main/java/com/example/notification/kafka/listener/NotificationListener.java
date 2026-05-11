package com.example.notification.kafka.listener;

import com.example.notification.kafka.event.EtudiantEvent;
import com.example.notification.kafka.event.NoteEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationListener {

    @KafkaListener(
            topics = "etudiant-created",
            groupId = "notification-group",
            properties = {
                    "spring.json.value.default.type=com.example.notification.kafka.event.EtudiantEvent",
                    "spring.json.use.type.headers=false"
            }
    )
    public void onEtudiantCreated(@Payload EtudiantEvent event) {
        log.info("📧 [NOTIFICATION] Nouvel étudiant inscrit : {} (ID: {}). Email de bienvenue simulé envoyé à {}",
                event.getNom(), event.getEtudiantId(), event.getEmail());
    }

    @KafkaListener(
            topics = "note-created",
            groupId = "notification-group",
            properties = {
                    "spring.json.value.default.type=com.example.notification.kafka.event.NoteEvent",
                    "spring.json.use.type.headers=false"
            }
    )
    public void onNoteCreated(@Payload NoteEvent event) {
        log.info("📧 [NOTIFICATION] Nouvelle note enregistrée pour l'étudiant ID {} — Matière : {}, Valeur : {}/20",
                event.getStudentId(), event.getMatiere(), event.getValeur());
    }
}
