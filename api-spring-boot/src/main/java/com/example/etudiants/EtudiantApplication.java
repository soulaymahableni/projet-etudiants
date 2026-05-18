package com.example.etudiants;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Partie 6 - Q2 : architecture Kubernetes-native.
 * L'annotation @EnableDiscoveryClient (Eureka) a ete retiree :
 * la decouverte de services est desormais assuree par Kubernetes
 * via les Services et le DNS interne du cluster.
 */
@SpringBootApplication
@EnableCaching
public class EtudiantApplication {
    public static void main(String[] args) {
        SpringApplication.run(EtudiantApplication.class, args);
    }
}
