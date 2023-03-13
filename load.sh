#!/bin/bash
for i in `seq 1 1000`;
do
#    curl -s -o /dev/null -I -w "%{http_code}" $1
    curl --header "Content-Type: application/json" --request POST --data '{"customerId":1,"numberOfCars":2}' http://localhost:31380/carservice/cars
    echo
done
