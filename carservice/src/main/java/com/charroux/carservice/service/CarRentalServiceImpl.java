package com.charroux.carservice.service;

import com.charroux.carservice.persistence.CarJPA;
import com.charroux.carservice.web.Car;
import com.charroux.carservice.web.CarNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CarRentalServiceImpl implements CarService{

    CarRepository carRepository;

    @Autowired
    public CarRentalServiceImpl(CarRepository carRepository) {
        super();
        this.carRepository = carRepository;
    }

    public void addCar(Car car){
        CarJPA carJPA = new CarJPA();
        carJPA.setRented(false);
        carJPA.setPrice(car.getPrice());
        carJPA.setPlateNumber(car.getPlateNumber());
        carJPA.setBrand(car.getBrand());
        carRepository.save(carJPA);
    }

    @Override
    public void rent(String plateNumber) throws CarNotFoundException {
        List<CarJPA> cars = carRepository.findByPlateNumber(plateNumber);
        if(cars.size() == 0){
            throw new CarNotFoundException();
        }
        CarJPA carJPA = cars.get(0);
        carJPA.setRented(true);
        carRepository.save(carJPA);
    }
    @Override
    public void getBack(String plateNumber) throws CarNotFoundException {
        List<CarJPA> cars = carRepository.findByPlateNumber(plateNumber);
        if(cars.size() == 0){
            throw new CarNotFoundException();
        }
        CarJPA carJPA = cars.get(0);
        carJPA.setRented(false);
        carRepository.save(carJPA);
    }

    @Override
    public List<Car> carToBeRented() {
        List<Car> c = new ArrayList<Car>();
        Iterator<CarJPA> cars = carRepository.findAll().iterator();
        while (cars.hasNext()){
            CarJPA next = cars.next();
            if(next.isRented() == false){
                Car car = new Car(next.getPlateNumber(), next.getBrand(), next.getPrice());
                c.add(car);
            }
        }
        return c;
    }
}
