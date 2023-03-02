package com.charroux.carservice.web;

public class RentCarsRequest {

    long customerId;
    int numberOfCars;

    public RentCarsRequest() {
    }

    public RentCarsRequest(long customerId, int numberOfCars) {
        this.customerId = customerId;
        this.numberOfCars = numberOfCars;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public int getNumberOfCars() {
        return numberOfCars;
    }

    public void setNumberOfCars(int numberOfCars) {
        this.numberOfCars = numberOfCars;
    }

    @Override
    public String toString() {
        return "RentCarsRequest{" +
                "customerId='" + customerId + '\'' +
                ", numberOfCars=" + numberOfCars +
                '}';
    }
}
