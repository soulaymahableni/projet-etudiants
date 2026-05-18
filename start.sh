#!/usr/bin/env bash
# Script de demarrage rapide (Partie 6 - architecture Kubernetes-native)
set -e

echo "Demarrage de la stack complete (mode developpement local Docker Compose)"
echo ""
echo "Services lances :"
echo "  - PostgreSQL"
echo "  - Redis"
echo "  - MongoDB"
echo "  - etudiant-service"
echo "  - grading-service"
echo "  - auth-service"
echo "  - Frontend Next.js"
echo ""
echo "Note Partie 6 : Eureka et API Gateway ont ete supprimes."
echo "Le DNS interne Docker (ou Kubernetes en prod) joue ce role."
echo ""

docker compose up --build

# Apres demarrage :
# - Frontend         : http://localhost:3000
# - Etudiant API     : http://localhost:8081/swagger-ui.html
# - Grading API      : http://localhost:8082/swagger-ui.html
# - Auth API         : http://localhost:3001
