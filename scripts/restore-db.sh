#!/bin/bash
# Script de restauracion de la base de datos
# Uso: ./restore-db.sh /root/backups/nombre_backup.sql.gz

if [ -z "$1" ]; then
    echo "Uso: $0 <archivo_backup.sql.gz>"
    echo "Backups disponibles:"
    ls /root/backups/*.sql.gz 2>/dev/null
    exit 1
fi

ARCHIVO=$1

if [ ! -f "$ARCHIVO" ]; then
    echo "Error: el archivo $ARCHIVO no existe"
    exit 1
fi

echo "Restaurando backup: $ARCHIVO"
gunzip -c $ARCHIVO | mysql -u gympass -p'GymPass2026!' db_reto
echo "Restauracion completada"