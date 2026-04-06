#!/bin/bash
# Script de despliegue del backend Spring Boot
# Uso: ./scripts/deploy-backend.sh
# Requisitos: Maven instalado, acceso SSH al servidor

echo "Compilando backend..."
cd backend
./mvnw clean package -DskipTests

echo "Subiendo JAR al servidor..."
scp target/backend_gympass-0.0.1-SNAPSHOT.jar root@gympass.aarondh.com:/root/

echo "Reiniciando servicio en el servidor..."
ssh root@gympass.aarondh.com "systemctl restart gympass"

echo "Despliegue del backend completado."