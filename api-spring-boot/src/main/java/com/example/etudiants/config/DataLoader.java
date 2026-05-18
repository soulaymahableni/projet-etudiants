package com.example.etudiants.config;

import com.example.etudiants.entity.Departement;
import com.example.etudiants.entity.Etudiant;
import com.example.etudiants.repository.DepartementRepository;
import com.example.etudiants.repository.EtudiantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Profile("!test")
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final EtudiantRepository etudiantRepository;
    private final DepartementRepository departementRepository;

    @Override
    public void run(String... args) {
        if (etudiantRepository.count() > 0) {
            log.info("Donnees deja presentes, seed ignore.");
            return;
        }

        Departement info = departementRepository.save(Departement.builder().nom("Informatique").build());
        Departement math = departementRepository.save(Departement.builder().nom("Mathematiques").build());
        Departement phys = departementRepository.save(Departement.builder().nom("Physique").build());

        List<Etudiant> etudiants = List.of(
                Etudiant.builder().cin("11111111").nom("Alice Martin")
                        .dateNaissance(LocalDate.of(2002, 4, 7)).email("alice@example.com")
                        .anneePremiereInscription(2021).departement(info).build(),
                Etudiant.builder().cin("22222222").nom("Bob Dupont")
                        .dateNaissance(LocalDate.of(2001, 9, 15)).email("bob@example.com")
                        .anneePremiereInscription(2020).departement(info).build(),
                Etudiant.builder().cin("33333333").nom("Carla Benali")
                        .dateNaissance(LocalDate.of(2003, 1, 22)).email("carla@example.com")
                        .anneePremiereInscription(2022).departement(math).build(),
                Etudiant.builder().cin("44444444").nom("David Sassi")
                        .dateNaissance(LocalDate.of(2000, 12, 3)).email("david@example.com")
                        .anneePremiereInscription(2019).departement(phys).build(),
                Etudiant.builder().cin("55555555").nom("Emma Trabelsi")
                        .dateNaissance(LocalDate.of(2002, 6, 30)).email("emma@example.com")
                        .anneePremiereInscription(2021).departement(math).build()
        );

        etudiantRepository.saveAll(etudiants);
        log.info("Seed termine : {} etudiants, {} departements", etudiants.size(), 3);
    }
}
