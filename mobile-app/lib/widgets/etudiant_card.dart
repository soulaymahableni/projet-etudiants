import 'package:flutter/material.dart';
import '../models/etudiant.dart';

class EtudiantCard extends StatelessWidget {
  final Etudiant e;
  const EtudiantCard({super.key, required this.e});

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
      child: ListTile(
        leading: CircleAvatar(child: Text(e.nom.isNotEmpty ? e.nom[0] : '?')),
        title: Text(e.nom, style: const TextStyle(fontWeight: FontWeight.bold)),
        subtitle: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text('CIN : ${e.cin}'),
            Text('Naissance : ${e.dateNaissance}${e.age != null ? '  (${e.age} ans)' : ''}'),
            if (e.departementNom != null) Text('Dep : ${e.departementNom}'),
          ],
        ),
        trailing: e.email != null
            ? IconButton(icon: const Icon(Icons.email), onPressed: () {})
            : null,
      ),
    );
  }
}
