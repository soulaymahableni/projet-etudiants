import 'package:flutter/material.dart';
import 'screens/home_screen.dart';

void main() => runApp(const EtudiantsApp());

class EtudiantsApp extends StatelessWidget {
  const EtudiantsApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Etudiants',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.indigo),
        useMaterial3: true,
      ),
      home: const HomeScreen(),
    );
  }
}
