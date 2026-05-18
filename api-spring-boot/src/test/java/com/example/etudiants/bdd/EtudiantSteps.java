package com.example.etudiants.bdd;

import com.example.etudiants.entity.Etudiant;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Quand;
import io.cucumber.java.fr.Soit;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class EtudiantSteps {

    private Etudiant etudiant;
    private int ageCalcule;

    @Soit("un etudiant avec la date de naissance {string}")
    public void unEtudiantAvecDateNaissance(String date) {
        etudiant = Etudiant.builder()
                .nom("Test")
                .cin("00000000")
                .dateNaissance(LocalDate.parse(date))
                .build();
    }

    @Quand("on calcule son age")
    public void onCalculeSonAge() {
        ageCalcule = etudiant.age();
    }

    @Alors("l'age retourne doit etre superieur ou egal a {int}")
    public void ageDoitEtreSuperieurOuEgalA(int ageMin) {
        assertThat(ageCalcule).isGreaterThanOrEqualTo(ageMin);
    }
}
