package com.charroux.carservice.service;

import com.charroux.carservice.web.Car;
import com.charroux.carservice.web.CarNotFoundException;

import java.util.List;

public interface CarService {
    public void addCar(Car car);
    public void rent(String plateNumber) throws CarNotFoundException;
    public void getBack(String plateNumber) throws CarNotFoundException;
    public List<Car> carToBeRented();
}
