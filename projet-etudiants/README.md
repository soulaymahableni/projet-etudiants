# 🎓 Projet Etudiants - Architecture Microservices

Projet d'integration de competences couvrant les **4 parties** :
1. **API REST Spring Boot** (etudiant-service) + PostgreSQL + Docker + Mobile Flutter
2. Methode `age()`, BDD Cucumber, Redis cache, Swagger, Departement, Kubernetes
3. Microservices : Eureka, Feign, API Gateway, grading-service, frontend Next.js
4. Tests complets (unit, integration Testcontainers, E2E Cypress, stress Gatling), JaCoCo >= 80%, auth-service Node/Express/JWT/MongoDB

---

## 📁 Structure du projet

```
projet-etudiants/
├── api-spring-boot/      # Microservice etudiants (Partie 1+2)
├── grading-service/      # Microservice notes (Partie 3)
├── eureka-server/        # Service registry (Partie 3)
├── api-gateway/          # API Gateway Spring Cloud (Partie 3)
├── auth-service/         # Auth JWT Node.js + MongoDB (Partie 4)
├── frontend/             # Frontend Next.js + Tailwind (Partie 3)
├── mobile-app/           # Application Flutter (Partie 1+3)
├── k8s/                  # Manifests Kubernetes (Partie 2)
├── .github/              # Templates issues/PR + workflow CI
└── docker-compose.yml    # Orchestration globale
```

---

## ⚡ Demarrage rapide (le tout-en-un)

### Prerequis
- Docker + Docker Compose
- (Optionnel pour dev local) JDK 21, Maven 3.9+, Node 20+, Flutter 3.3+

### Lancer toute la stack

```bash
docker compose up --build
```

Services disponibles :
| Service | URL | Description |
|---------|-----|-------------|
| Frontend Next.js | http://localhost:3000 | UI principale |
| API Gateway | http://localhost:8080 | Point d'entree unique |
| Etudiant Service | http://localhost:8081 | Direct (debug) |
| Grading Service | http://localhost:8082 | Direct (debug) |
| Auth Service | http://localhost:3001 | Auth JWT |
| Eureka Dashboard | http://localhost:8761 | Discovery |
| Swagger Etudiants | http://localhost:8081/swagger-ui.html | API docs |
| Swagger Notes | http://localhost:8082/swagger-ui.html | API docs |
| Page HTML | http://localhost:8081 | index.html (Q4 P2) |

### Tester rapidement
```bash
# Lister les etudiants via le gateway
curl http://localhost:8080/api/etudiants

# Filtrer par annee (Q9 P2)
curl "http://localhost:8080/api/etudiants?annee=2021"

# Creer un departement
curl -X POST http://localhost:8080/api/departements \
  -H 'Content-Type: application/json' \
  -d '{"nom":"Genie civil"}'

# S'inscrire (auth-service)
curl -X POST http://localhost:8080/auth/register \
  -H 'Content-Type: application/json' \
  -d '{"username":"alice","password":"secret123"}'

# Se connecter
curl -X POST http://localhost:8080/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"username":"alice","password":"secret123"}'
```

---

## 🔧 Demarrage par partie

### Partie 1 : API + DB + Mobile

```bash
# Demarrer juste l'API + Postgres
docker compose up --build postgres etudiant-service

# Lancer l'app mobile (depuis un autre terminal)
cd mobile-app
flutter pub get
flutter run                                            # Android emulateur
flutter run --dart-define=API_URL=http://localhost:8080  # iOS simulateur
```

### Partie 2 : Tests BDD + Redis + K8s + Image Docker

```bash
# Tests BDD Cucumber (Q3 P2)
cd api-spring-boot
mvn test -Dtest=CucumberTestRunner

# Construire et publier l'image (Q5 P2 - remplacer USERNAME)
docker build -t USERNAME/etudiant-service:1.0 ./api-spring-boot
docker push USERNAME/etudiant-service:1.0

# Deployer sur K3S (Q6 P2)
kubectl apply -f k8s/postgres-deployment.yaml
kubectl apply -f k8s/etudiant-deployment.yaml
kubectl get pods
kubectl port-forward svc/etudiant-service 8081:8081
```

### Partie 3 : Microservices complets
Tout se demarre via `docker compose up --build` (voir tableau plus haut).

### Partie 4 : Tests complets

```bash
cd api-spring-boot

# Tests unitaires + integration (Testcontainers => Docker requis)
mvn verify

# Couverture JaCoCo (echec si < 80%) - rapport dans target/site/jacoco/index.html
mvn verify
open target/site/jacoco/index.html

# Tests de stress Gatling (api doit tourner sur :8080)
mvn gatling:test -Dgatling.simulationClass=simulations.EtudiantSimulation

# Tests E2E Cypress (toute la stack doit etre demarree)
cd ../frontend
npm install
npm run cypress:open       # mode interactif
npm run cypress:run        # mode headless
```

---

## 🧪 Strategie de tests (Partie 4)

| Niveau | Outil | Localisation |
|--------|-------|--------------|
| Unitaires | JUnit 5 + Mockito | `api-spring-boot/src/test/java/com/example/etudiants/unit/` |
| BDD | Cucumber + JUnit 5 | `api-spring-boot/src/test/resources/features/` |
| Integration | Testcontainers + PostgreSQL | `api-spring-boot/src/test/java/com/example/etudiants/integration/` |
| E2E | Cypress | `frontend/cypress/e2e/` |
| Stress | Gatling | `api-spring-boot/src/gatling/java/simulations/` |

**Couverture minimale : 80%** (configuree via `jacoco-maven-plugin` dans `pom.xml`).

---

## 🔌 Convention de commits Jira

Tous les commits doivent inclure la cle Jira pour la tracabilite GitHub <-> Jira (Q4 P4) :
```bash
git commit -m "PROJ-12 : ajout methode age() sur entite Etudiant"
git checkout -b feature/PROJ-23-tests-unitaires
```

---

## 👥 Convention de review (Q2 P3)

- Toute PR doit etre relue dans les 48h
- Au moins une approbation requise avant merge
- Les commentaires bloquants doivent etre resolus
- La branche `main` est protegee (push direct interdit)
- La PR doit referencer un ticket Jira (`PROJ-XX`)

> ⚠️ **A configurer manuellement** dans GitHub Settings > Branches : protection de `main` et des branches `version-*`, exigence de PR + review.

---

## 📋 Suivi Jira (Sprints 1-4)

A configurer manuellement dans Jira Cloud :

```
Epic : Gestion des Etudiants
├── Sprint 1 - API REST de base (Partie 1)
├── Sprint 2 - Enrichissement (Partie 2)
├── Sprint 3 - Microservices (Partie 3)
└── Sprint 4 - Qualite logicielle (Partie 4)
```

> 📸 Joindre la capture d'ecran du board Jira ici une fois cree.

---

## 🐛 Troubleshooting

### Le frontend n'affiche pas les etudiants
- Verifier que l'API gateway tourne : `curl http://localhost:8080/api/etudiants`
- Sur Linux, ajouter `extra_hosts: ["host.docker.internal:host-gateway"]` au frontend si necessaire.

### `Testcontainers could not start`
- Docker Desktop doit etre actif sur la machine ou tourne `mvn verify`.

### Mobile : "Connection refused"
- Android emulateur : utiliser `10.0.2.2` au lieu de `localhost`
- iOS simulateur : `localhost` fonctionne
- Device physique : utiliser l'IP locale `192.168.x.x`

### Eureka : services non enregistres
- Attendre 30-60s apres demarrage : Eureka a un cycle de heartbeat lent
- Verifier le dashboard sur http://localhost:8761

### Build Maven echoue sur la couverture
- Si < 80%, soit ajouter des tests, soit baisser temporairement `<minimum>0.80</minimum>` dans `pom.xml`

---

## 📜 Licence

Projet pedagogique - Formateur : Wahid Hamdi
