package com.example.etudiants.integration;

import com.example.etudiants.entity.Departement;
import com.example.etudiants.entity.Etudiant;
import com.example.etudiants.repository.DepartementRepository;
import com.example.etudiants.repository.EtudiantRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
class EtudiantIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15-alpine")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");

    @DynamicPropertySource
    static void registerProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired private EtudiantRepository etudiantRepository;
    @Autowired private DepartementRepository departementRepository;

    @AfterEach
    void cleanUp() {
        etudiantRepository.deleteAll();
        departementRepository.deleteAll();
    }

    @Test
    void shouldPersistAndRetrieveEtudiant() {
        Departement dep = departementRepository.save(Departement.builder().nom("Info").build());
        Etudiant e = Etudiant.builder()
                .cin("ABC123").nom("Test")
                .dateNaissance(LocalDate.of(2000, 1, 1))
                .anneePremiereInscription(2020)
                .departement(dep).build();
        etudiantRepository.save(e);

        List<Etudiant> all = etudiantRepository.findAll();
        assertThat(all).hasSize(1);
        assertThat(all.get(0).getCin()).isEqualTo("ABC123");
    }

    @Test
    void findByAnneePremiereInscription_returnsFiltered() {
        Etudiant e1 = Etudiant.builder().cin("A1").nom("X")
                .dateNaissance(LocalDate.of(2001, 1, 1))
                .anneePremiereInscription(2020).build();
        Etudiant e2 = Etudiant.builder().cin("A2").nom("Y")
                .dateNaissance(LocalDate.of(2002, 1, 1))
                .anneePremiereInscription(2021).build();
        etudiantRepository.saveAll(List.of(e1, e2));

        List<Etudiant> result = etudiantRepository.findByAnneePremiereInscription(2020);
        assertThat(result).hasSize(1).first()
                .extracting(Etudiant::getCin).isEqualTo("A1");
    }

    @Test
    void findByCin_works() {
        Etudiant e = Etudiant.builder().cin("CINX").nom("Z")
                .dateNaissance(LocalDate.of(2001, 1, 1))
                .anneePremiereInscription(2020).build();
        etudiantRepository.save(e);

        assertThat(etudiantRepository.findByCin("CINX")).isPresent();
        assertThat(etudiantRepository.existsByCin("CINX")).isTrue();
    }
}
