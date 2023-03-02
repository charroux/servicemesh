package com.charroux.carservice.service;

import com.charroux.carservice.entity.Car;
import com.charroux.carservice.entity.RentalAgreement;
import com.charroux.carservice.web.CarNotFoundException;

import java.util.List;

public interface RentalService {
    public RentalAgreement rent(long customerId, int numberOfCars) throws CarNotFoundException;
    public List<Car> carsToBeRented();
    public List<RentalAgreement> getAgreements();
}
