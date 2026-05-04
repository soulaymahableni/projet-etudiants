class Etudiant {
  final int id;
  final String cin;
  final String nom;
  final String dateNaissance;
  final String? email;
  final int? anneePremiereInscription;
  final int? departementId;
  final String? departementNom;
  final int? age;

  Etudiant({
    required this.id,
    required this.cin,
    required this.nom,
    required this.dateNaissance,
    this.email,
    this.anneePremiereInscription,
    this.departementId,
    this.departementNom,
    this.age,
  });

  factory Etudiant.fromJson(Map<String, dynamic> json) {
    return Etudiant(
      id: json['id'] as int,
      cin: json['cin'] ?? '',
      nom: json['nom'] ?? '',
      dateNaissance: json['dateNaissance'] ?? '',
      email: json['email'],
      anneePremiereInscription: json['anneePremiereInscription'],
      departementId: json['departementId'],
      departementNom: json['departementNom'],
      age: json['age'],
    );
  }
}
