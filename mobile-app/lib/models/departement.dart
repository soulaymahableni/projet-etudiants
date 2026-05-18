class Departement {
  final int id;
  final String nom;

  Departement({required this.id, required this.nom});

  factory Departement.fromJson(Map<String, dynamic> json) =>
      Departement(id: json['id'] as int, nom: json['nom'] ?? '');
}
