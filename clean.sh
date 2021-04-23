#!/usr/bin/env bash

set -eux
set -o pipefail
set -o errexit

echo "Hi! This small script is going to clean your PostgreSQL database and Elasticsearch index and re-import everything to make sure you have the latest proper setup"

echo """
SELECT pg_terminate_backend(pg_stat_activity.pid)
FROM pg_stat_activity
WHERE pg_stat_activity.datname = 'podcast'
AND pid <> pg_backend_pid();

DROP DATABASE IF EXISTS podcast;
CREATE DATABASE podcast;
""" >> temp.sql
docker cp ./temp.sql postgres:/temp.sql
docker exec -u postgres postgres psql postgres postgres -f /temp.sql

rm temp.sql

echo "Remove Elasticsearch index"
curl -XDELETE http://localhost:9200/shows
curl -XDELETE http://localhost:9200/episodes

echo "Done!"
