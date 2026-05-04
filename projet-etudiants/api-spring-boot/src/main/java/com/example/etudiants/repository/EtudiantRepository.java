package com.example.etudiants.repository;

import com.example.etudiants.entity.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {

    /** Q9 - Partie 2 : recherche par annee de premiere inscription. */
    List<Etudiant> findByAnneePremiereInscription(int annee);

    List<Etudiant> findByDepartementId(Long departementId);

    Optional<Etudiant> findByCin(String cin);

    boolean existsByCin(String cin);
}
