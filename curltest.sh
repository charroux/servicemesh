#!/bin/sh


curl --header "Content-Type: application/json" --request POST --data '{"customerId":1,"numberOfCars":2}' http://localhost:31380/carservice/cars

curl --header "Content-Type: application/json" --request POST --data '{"customerId":1,"numberOfCars":1}' http://localhost:31380/carservice/cars
