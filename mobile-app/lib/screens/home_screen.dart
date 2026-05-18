import 'package:flutter/material.dart';
import '../models/departement.dart';
import '../models/etudiant.dart';
import '../services/api_service.dart';
import '../widgets/etudiant_card.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});
  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  final ApiService api = ApiService();
  Future<List<Departement>>? _depFuture;
  Future<List<Etudiant>>? _etudiantsFuture;
  Departement? _selectedDep;

  @override
  void initState() {
    super.initState();
    _depFuture = api.fetchDepartements();
    _etudiantsFuture = api.fetchEtudiants();
  }

  void _onDepChanged(Departement? dep) {
    setState(() {
      _selectedDep = dep;
      _etudiantsFuture = api.fetchEtudiants(departementId: dep?.id);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Etudiants'),
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
      ),
      body: Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(12.0),
            child: FutureBuilder<List<Departement>>(
              future: _depFuture,
              builder: (context, snap) {
                if (!snap.hasData) return const LinearProgressIndicator();
                return DropdownButtonFormField<Departement?>(
                  decoration: const InputDecoration(
                    labelText: 'Filtrer par departement',
                    border: OutlineInputBorder(),
                  ),
                  value: _selectedDep,
                  items: [
                    const DropdownMenuItem<Departement?>(
                        value: null, child: Text('Tous les departements')),
                    ...snap.data!.map((d) => DropdownMenuItem<Departement?>(
                          value: d, child: Text(d.nom),
                        )),
                  ],
                  onChanged: _onDepChanged,
                );
              },
            ),
          ),
          Expanded(
            child: FutureBuilder<List<Etudiant>>(
              future: _etudiantsFuture,
              builder: (context, snap) {
                if (snap.connectionState == ConnectionState.waiting) {
                  return const Center(child: CircularProgressIndicator());
                }
                if (snap.hasError) {
                  return Center(child: Text('Erreur : ${snap.error}'));
                }
                final list = snap.data ?? [];
                if (list.isEmpty) {
                  return const Center(child: Text('Aucun etudiant.'));
                }
                return ListView.builder(
                  itemCount: list.length,
                  itemBuilder: (_, i) => EtudiantCard(e: list[i]),
                );
              },
            ),
          ),
        ],
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          setState(() {
            _etudiantsFuture = api.fetchEtudiants(departementId: _selectedDep?.id);
          });
        },
        child: const Icon(Icons.refresh),
      ),
    );
  }
}
