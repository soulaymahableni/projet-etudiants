package com.example.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Micro service de notifications.
 * Consomme les événements Kafka publiés par etudiant-service et grading-service,
 * et simule l'envoi de notifications (email, push, etc.) via des logs.
 *
 * Partie 5 - Q2 : Communication asynchrone via Kafka
 */
@SpringBootApplication
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }
}