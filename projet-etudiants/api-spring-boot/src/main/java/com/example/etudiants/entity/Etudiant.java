package com.example.etudiants.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "etudiants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "departement")
public class Etudiant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String cin;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @Column(length = 150)
    private String email;

    @Column(name = "annee_premiere_inscription")
    private int anneePremiereInscription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departement_id")
    private Departement departement;

    /**
     * Calcule dynamiquement l'age de l'etudiant a partir de sa date de naissance.
     * Q2 - Partie 2.
     */
    public int age() {
        if (dateNaissance == null) {
            return 0;
        }
        return Period.between(this.dateNaissance, LocalDate.now()).getYears();
    }
}
