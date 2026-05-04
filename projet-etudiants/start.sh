#!/usr/bin/env bash
# Script de demarrage rapide
set -e

echo "🚀 Demarrage de la stack complete..."
echo ""
echo "Cela va lancer :"
echo "  - PostgreSQL (etudiants)"
echo "  - PostgreSQL (notes)"
echo "  - Redis"
echo "  - MongoDB"
echo "  - Eureka Server"
echo "  - etudiant-service"
echo "  - grading-service"
echo "  - auth-service"
echo "  - API Gateway"
echo "  - Frontend Next.js"
echo ""
echo "Ce sera long la premiere fois (build Maven + Next.js)."
echo ""

docker compose up --build

# Apres demarrage :
# - Frontend : http://localhost:3000
# - API : http://localhost:8080/api/etudiants
# - Eureka : http://localhost:8761
# - Swagger : http://localhost:8081/swagger-ui.html
