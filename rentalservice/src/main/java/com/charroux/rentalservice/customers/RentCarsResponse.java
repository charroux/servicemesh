package com.charroux.rentalservice.customers;

public class RentCarsResponse {

    int customerID;
    long rentalAgreementId;
    String state;

    public RentCarsResponse() { }

    public RentCarsResponse(int customerID, long rentalAgreementId, String state) {
        this.customerID = customerID;
        this.rentalAgreementId = rentalAgreementId;
        this.state = state;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public long getRentalAgreementId() {
        return rentalAgreementId;
    }

    public void setRentalAgreementId(long rentalAgreementId) {
        this.rentalAgreementId = rentalAgreementId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
