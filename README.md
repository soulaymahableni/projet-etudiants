# Projet Etudiants — Plateforme Micro Services

## Description

Plateforme complete de gestion des etudiants developpee en architecture micro services
dans le cadre d'une formation en genie logiciel / DevOps. Le projet illustre l'integralite
du cycle de vie d'une application moderne : developpement local, tests automatises,
conteneurisation, orchestration Kubernetes et deploiement cloud.

La **Partie 6** marque la transition vers une **architecture Kubernetes-native** :
Eureka, Feign et Spring Cloud Gateway ont ete remplaces par les primitives natives de
Kubernetes (Services, DNS interne, Ingress). L'ensemble est packagise sous Helm et
pret a etre deploye sur AWS EKS.

---

## Architecture

```
                       Internet / utilisateurs
                                |
                                v
                     +----------------------+
                     |   Ingress (Traefik)  |   <-- remplace l'API Gateway
                     +----------+-----------+
                                |
        +-----------------------+-----------------------+
        |                       |                       |
        v                       v                       v
  +-----------+         +-----------+           +-----------+
  | Frontend  |         |   Auth    |           |  Etudiant |
  |  Next.js  |         |  Node.js  |           | SpringBoot|
  +-----------+         +-----+-----+           +-----+-----+
                              |                       |
                              v                       v
                         +---------+              +--------+
                         | MongoDB |              | Redis  |  (cache)
                         +---------+              +--------+
                                                       |
                                                       v
                              +-----------------------+
                              |       PostgreSQL       |
                              +-----------------------+
                                          ^
                                          |
                              +-----------+-----------+
                              |  Grading (SpringBoot) |
                              |  (RestClient -> DNS K8s)
                              +-----------------------+
```

Le `grading-service` appelle l'`etudiant-service` via le **DNS interne Kubernetes** :
`http://etudiant-service:8081`. Plus besoin d'Eureka : Kubernetes resout
ce nom vers le bon pod et load-balance entre les replicas via `kube-proxy`.

---

## Stack technique

| Composant              | Technologie                                   |
|------------------------|-----------------------------------------------|
| Micro service etudiant | Spring Boot 3.3, Java 21, PostgreSQL, Redis   |
| Micro service notes    | Spring Boot 3.3, Java 21, PostgreSQL, RestClient |
| Micro service auth     | Node.js, Express, MongoDB, JWT                |
| Frontend               | Next.js, Tailwind CSS                         |
| Application mobile     | Flutter                                       |
| Orchestration          | Kubernetes (K3S / EKS), Helm 3                |
| Bus de messages        | Apache Kafka (optionnel)                      |
| CI/CD                  | GitHub Actions, Jira                          |
| Observabilite          | Spring Actuator (health, readiness, liveness) |

---

## Lancement rapide

### Prerequis

- Docker Desktop >= 24.0
- Java 21, Maven 3.9+
- Node.js 20+
- kubectl, Helm 3 (pour K8s)
- K3S installe (pour le deploiement Kubernetes local)

---

### Option 1 — Docker Compose (developpement local)

```bash
git clone https://github.com/<username>/projet-etudiants.git
cd projet-etudiants
docker compose up --build
```

Acces :

| Service           | URL                                |
|-------------------|------------------------------------|
| Frontend          | http://localhost:3000              |
| Etudiant API      | http://localhost:8081/swagger-ui.html |
| Grading API       | http://localhost:8082/swagger-ui.html |
| Auth API          | http://localhost:3001              |

---

### Option 2 — Kubernetes brut (K3S)

```bash
# 1. Secrets
kubectl apply -f k8s/secrets/

# 2. Stockage (PVC)
kubectl apply -f k8s/postgres/postgres-pvc.yaml
kubectl apply -f k8s/mongodb/mongodb-pvc.yaml

# 3. Infrastructure
kubectl apply -f k8s/postgres/
kubectl apply -f k8s/mongodb/
kubectl apply -f k8s/redis/
kubectl apply -f k8s/zookeeper/
kubectl apply -f k8s/kafka/

# 4. Micro services
kubectl apply -f k8s/etudiant-service/
kubectl apply -f k8s/grading-service/
kubectl apply -f k8s/auth-service/
kubectl apply -f k8s/frontend/

# 5. Ingress
kubectl apply -f k8s/ingress.yaml

# Verification
kubectl get pods,services,ingress
```

Ajouter dans `/etc/hosts` :
```
<IP-VM-K3S>   projet-etudiants.local
```

Acces : http://projet-etudiants.local

---

### Option 3 — Helm (recommandee)

```bash
# Validation
helm template projet-etudiants ./helm/projet-etudiants/

# Premiere installation (dev)
helm install projet-etudiants ./helm/projet-etudiants/

# Mise a jour
helm upgrade projet-etudiants ./helm/projet-etudiants/

# Production (surcharge values-prod.yaml)
helm upgrade --install projet-etudiants ./helm/projet-etudiants/ \
  -f ./helm/projet-etudiants/values-prod.yaml

# Etat du deploiement
helm status projet-etudiants
kubectl get pods --watch

# Desinstallation propre
helm uninstall projet-etudiants
```

---

## Architecture AWS Cloud (production)

| Composant local         | Service AWS managé             |
|-------------------------|--------------------------------|
| Cluster K3S             | **Amazon EKS**                 |
| Pod PostgreSQL          | **Amazon RDS for PostgreSQL**  |
| Pod Redis               | **Amazon ElastiCache**         |
| Pod MongoDB             | **Amazon DocumentDB**          |
| Pods Kafka + Zookeeper  | **Amazon MSK**                 |
| Ingress Traefik         | **AWS ALB** + ALB Controller   |
| Images Docker           | **Amazon ECR**                 |
| Kubernetes Secrets      | **AWS Secrets Manager** + External Secrets Operator |
| TLS                     | **AWS ACM**                    |
| DNS public              | **Amazon Route 53**            |

Deploiement EKS :
```bash
aws eks update-kubeconfig --name projet-etudiants-cluster --region eu-west-1
helm upgrade --install projet-etudiants ./helm/projet-etudiants/ \
  -f ./helm/projet-etudiants/values-prod.yaml
```

---

## Tests

```bash
# Tests unitaires + integration + couverture JaCoCo (>= 80%)
cd api-spring-boot && mvn verify

# Tests E2E Cypress (stack Docker demarree au prealable)
cd frontend && npx cypress run

# Tests de stress Gatling
cd api-spring-boot && mvn gatling:test
```

---

## Structure du depot

```
projet-etudiants/
├── api-spring-boot/         # Micro service etudiant (Spring Boot)
├── grading-service/         # Micro service notes (Spring Boot + RestClient)
├── auth-service/            # Micro service auth (Node.js + Express + MongoDB)
├── frontend/                # Application Next.js
│   └── cypress/e2e/         # Tests E2E Cypress
├── mobile-app/              # Application Flutter
│
├── k8s/                     # Manifests Kubernetes bruts (avant Helm)
│   ├── secrets/
│   ├── postgres/            # Deployment + Service + PVC
│   ├── mongodb/             # Deployment + Service + PVC
│   ├── redis/
│   ├── zookeeper/
│   ├── kafka/
│   ├── etudiant-service/
│   ├── grading-service/
│   ├── auth-service/
│   ├── frontend/
│   └── ingress.yaml
│
├── helm/
│   └── projet-etudiants/    # Chart Helm packageant toute la plateforme
│       ├── Chart.yaml
│       ├── values.yaml      # Valeurs par defaut (dev)
│       ├── values-prod.yaml # Surcharge production (AWS)
│       └── templates/
│           ├── _helpers.tpl
│           ├── secrets.yaml
│           ├── postgres/
│           ├── mongodb/
│           ├── redis/
│           ├── zookeeper/
│           ├── kafka/
│           ├── etudiant-service/
│           ├── grading-service/
│           ├── auth-service/
│           ├── frontend/
│           └── ingress.yaml
│
├── docker-compose.yml       # Pour le developpement local
└── README.md                # Ce fichier
```

---

## Changements Partie 6 (architecture Kubernetes-native)

- **eureka-server/ supprime** -- remplace par le DNS interne Kubernetes
- **api-gateway/ supprime** -- remplace par un Ingress Traefik
- **Eureka client retire** des `pom.xml` (etudiant-service + grading-service)
- **@EnableDiscoveryClient retire** des classes `Application`
- **@FeignClient retire** -- remplace par un `RestClient` Spring natif
- **application.yml nettoyes** des blocs `eureka:`
- **Probes K8s ajoutees** : `readinessProbe` et `livenessProbe` via Spring Actuator
- **Chart Helm cree** avec `values.yaml` (dev) et `values-prod.yaml` (prod AWS)
- **Manifests bruts** dans `k8s/` pour comprendre la structure avant Helm

---

## Auteur

Formation DevOps / Genie logiciel — 2025-2026
