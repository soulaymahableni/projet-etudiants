# language: fr
Fonctionnalite: Calcul de l'age d'un etudiant

  Scenario Outline: Calcul de l'age a partir de la date de naissance
    Soit un etudiant avec la date de naissance "<dateNaissance>"
    Quand on calcule son age
    Alors l'age retourne doit etre superieur ou egal a <ageMin>

    Examples:
      | dateNaissance | ageMin |
      | 2002-04-07    | 22     |
      | 2000-01-01    | 24     |
      | 2010-06-15    | 14     |
