#!/bin/bash
set -e

cd Code_generate

# preparation
# bash ./scripts/version.sh  # not integrated
bash ./scripts/setup_apache_config.sh code-generator.cub-it.org.conf ssl

cd -

docker-compose down --rmi local
docker-compose build
docker-compose up -d

sleep 20
echo "Init logs"
docker-compose logs
