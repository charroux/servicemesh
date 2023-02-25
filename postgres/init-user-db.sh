#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE dbcar;
    GRANT ALL PRIVILEGES ON DATABASE dbcar TO dbuser;
EOSQL
