package com.charroux.carservice.web;

import com.charroux.carservice.entity.RentalAgreement;

public class RentCarsResponse {

    long customerID;
    long rentalAgreementId;
    String state;

    public RentCarsResponse() { }

    public RentCarsResponse(long customerID, long rentalAgreementId, String state) {
        this.customerID = customerID;
        this.rentalAgreementId = rentalAgreementId;
        this.state = state;
    }

    public long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(long customerID) {
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
