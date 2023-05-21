package com.charroux.rentalservice.agreements;

import com.charroux.rentalservice.cars.Car;
import com.charroux.rentalservice.customers.Customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RentalAgreement {

    long id;
    Collection<Car> cars = new ArrayList<>();
    Customer customer;
    long customerId;

    public enum State{
        PENDING,
        CREDIT_RESERVED,
        CREDIT_REJECTED
    }

    State state;

    public RentalAgreement(long id) {
        this.id = id;
    }

    public RentalAgreement() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Collection<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", customer=" + customer +
                ", customerId=" + customerId +
                '}';
    }
}

/*public record RentalAgreement (String id, String plateNumber, String custumerId) {

    private static List<RentalAgreement> rentalAgreements = Arrays.asList(
            new RentalAgreement("rentalAgreement-1", "AA11BB", "customer-1")
    );

    public static RentalAgreement getById(String id) {
        return rentalAgreements.stream()
                .filter(rentalAgreement -> rentalAgreement.id().equals(id))
                .findFirst()
                .orElse(null);
    }
}*/