package com.example.grading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Partie 6 - Q2 : architecture Kubernetes-native.
 * - @EnableDiscoveryClient supprime : Kubernetes assure la decouverte.
 * - @EnableFeignClients supprime : remplace par un RestClient standard.
 */
@SpringBootApplication
public class GradingApplication {
    public static void main(String[] args) {
        SpringApplication.run(GradingApplication.class, args);
    }
}
