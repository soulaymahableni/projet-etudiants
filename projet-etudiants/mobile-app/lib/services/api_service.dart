import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/etudiant.dart';
import '../models/departement.dart';

class ApiService {
  // ATTENTION: Sur Android emulateur utiliser 10.0.2.2, sur iOS simulateur localhost,
  // sur device physique l'IP de la machine hote.
  static const String baseUrl = String.fromEnvironment(
    'API_URL',
    defaultValue: 'http://10.0.2.2:8080',
  );

  Future<List<Departement>> fetchDepartements() async {
    final res = await http.get(Uri.parse('$baseUrl/api/departements'));
    if (res.statusCode != 200) throw Exception('HTTP ${res.statusCode}');
    final List data = json.decode(utf8.decode(res.bodyBytes));
    return data.map((e) => Departement.fromJson(e)).toList();
  }

  Future<List<Etudiant>> fetchEtudiants({int? departementId}) async {
    final uri = departementId == null
        ? Uri.parse('$baseUrl/api/etudiants')
        : Uri.parse('$baseUrl/api/etudiants?departementId=$departementId');
    final res = await http.get(uri);
    if (res.statusCode != 200) throw Exception('HTTP ${res.statusCode}');
    final List data = json.decode(utf8.decode(res.bodyBytes));
    return data.map((e) => Etudiant.fromJson(e)).toList();
  }
}
