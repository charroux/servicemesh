package com.charroux.rentalservice.customers;

public class RentCarsRequest {

    int customerId;
    int numberOfCars;

    public RentCarsRequest() {
    }

    public RentCarsRequest(int customerId, int numberOfCars) {
        this.customerId = customerId;
        this.numberOfCars = numberOfCars;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
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
