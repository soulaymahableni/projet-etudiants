package com.example.grading.client;

import com.example.grading.dto.EtudiantSummaryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "etudiant-service")
public interface EtudiantClient {

    @GetMapping("/api/etudiants/{id}")
    EtudiantSummaryDTO getEtudiant(@PathVariable("id") Long id);
}
