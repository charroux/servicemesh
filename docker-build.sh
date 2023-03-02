#!/bin/sh
docker build --tag=carservice-postgres:1 postgres
docker build --tag=carservice:1 carservice
docker build --tag=carstat:1 carstat
