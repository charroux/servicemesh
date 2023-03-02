package com.charroux.carstat.entity;

public class CustomerApplication {

    long id;
    Customer customer;

    public enum State {
        CREDIT_RESERVED,
        CREDIT_REJECTED
    }

    State state;

    public CustomerApplication(State state) {
        this.state = state;
    }

    public CustomerApplication() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "CustomerApplication{" +
                "id=" + id +
                ", customer=" + customer +
                ", state=" + state +
                '}';
    }
}
