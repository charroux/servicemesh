#!/bin/sh
docker build --tag=carservice-postgres:1 postgres
docker build --tag=carservice:1 carservice
