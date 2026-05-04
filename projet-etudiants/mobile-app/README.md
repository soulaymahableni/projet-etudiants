# Application mobile Flutter

## Prerequis
- Flutter SDK 3.3+
- Android Studio ou Xcode

## Installation
```bash
cd mobile-app
flutter pub get
```

## Demarrage
**Important** : l'API doit etre demarree (via `docker compose up`).

### Sur emulateur Android
```bash
flutter run
```
L'URL par defaut est `http://10.0.2.2:8080` (alias de l'hote depuis l'emulateur Android).

### Sur simulateur iOS
```bash
flutter run --dart-define=API_URL=http://localhost:8080
```

### Sur appareil physique
Remplacer par l'IP de votre machine sur le reseau local :
```bash
flutter run --dart-define=API_URL=http://192.168.1.42:8080
```

## Structure
- `lib/models/` : modeles de donnees
- `lib/services/` : appels HTTP
- `lib/screens/` : ecrans
- `lib/widgets/` : composants reutilisables
