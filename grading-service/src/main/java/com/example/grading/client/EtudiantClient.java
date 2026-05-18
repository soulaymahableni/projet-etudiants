package com.example.grading.client;

import com.example.grading.dto.EtudiantSummaryDTO;
import com.example.grading.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import jakarta.annotation.PostConstruct;

/**
 * Partie 6 - Q2 : remplacement de @FeignClient par RestClient.
 *
 * L'URL d'etudiant-service est injectee depuis application.yml.
 * Aucune magie Eureka ici : Kubernetes resout "etudiant-service"
 * vers le bon pod via son DNS interne.
 */
@Component
@Slf4j
public class EtudiantClient {

    @Value("${clients.etudiant-service.url}")
    private String etudiantServiceUrl;

    private RestClient restClient;

    @PostConstruct
    void init() {
        this.restClient = RestClient.builder()
                .baseUrl(etudiantServiceUrl)
                .build();
        log.info("EtudiantClient initialise avec URL : {}", etudiantServiceUrl);
    }

    public EtudiantSummaryDTO getEtudiant(Long id) {
        try {
            return restClient.get()
                    .uri("/api/etudiants/{id}", id)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        if (res.getStatusCode().value() == 404) {
                            throw new ResourceNotFoundException("Etudiant", id);
                        }
                        throw new HttpClientErrorException(res.getStatusCode());
                    })
                    .body(EtudiantSummaryDTO.class);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (RestClientException e) {
            log.warn("Echec d'appel a etudiant-service : {}", e.getMessage());
            throw new IllegalStateException("Service etudiant indisponible", e);
        }
    }
}
