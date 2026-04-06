#!/bin/bash
# Script de despliegue del frontend Angular
# Uso: ./scripts/deploy-frontend.sh
# Requisitos: ng build instalado, acceso SSH al servidor

echo "Compilando frontend para produccion..."
cd frontend
ng build --configuration production

echo "Subiendo archivos al servidor..."
ssh root@gympass.aarondh.com "rm -rf /var/www/html/gympass/*"
scp -r dist/gympass-frontend/browser/* root@gympass.aarondh.com:/var/www/html/gympass/

echo "Despliegue del frontend completado."