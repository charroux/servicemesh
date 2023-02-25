#!/bin/sh
docker tag carservice:1 gcr.io/${PROJECT_ID}/carservice
docker push gcr.io/${PROJECT_ID}/carservice
