package com.charroux.carservice.service;

import com.charroux.carservice.entity.Car;
import com.charroux.carservice.entity.RentalAgreement;
import com.charroux.carservice.web.CarNotFoundException;
import com.charroux.carservice.web.CustomerNotFoundException;

import java.util.Collection;
import java.util.List;

public interface RentalService {
    public void addCar(Car car);
    public RentalAgreement rent(long customerId, int numberOfCars) throws CarNotFoundException;
    public List<Car> carsToBeRented();
    public List<RentalAgreement> getAgreements();
    public RentalAgreement getAgreement(long customerId) throws CustomerNotFoundException;
    public Collection<Car> getCars(RentalAgreement rentalAgreement);
}
