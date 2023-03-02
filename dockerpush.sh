#!/bin/sh
kubectl delete -f microservices.yaml

docker build --tag=carservice:1 carservice
docker tag carservice:1 efrei/carservice
docker push efrei/carservice

docker build --tag=carstat:1 carstat
docker tag carstat:1 efrei/carstat
docker push efrei/carstat

kubectl apply -f microservices.yaml
