#!/bin/bash
# Script de copia de seguridad de la base de datos
# Se ejecuta automaticamente cada dia a las 2:00 AM con cron
# Crontab: 0 2 * * * /root/scripts/backup-db.sh >> /var/log/gympass/backup.log 2>&1

FECHA=$(date +%Y%m%d_%H%M%S)
DIRECTORIO="/root/backups"
ARCHIVO="$DIRECTORIO/db_reto_$FECHA.sql"

mkdir -p $DIRECTORIO
mysqldump --no-tablespaces -u gympass -p'GymPass2026!' db_reto > $ARCHIVO
gzip $ARCHIVO
find $DIRECTORIO -name "*.sql.gz" -mtime +7 -delete
echo "Backup completado: $ARCHIVO.gz"