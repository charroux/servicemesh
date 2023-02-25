package com.charroux.carservice.web;

import com.charroux.carservice.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class CarRentalRestService {

    CarService carService;

    @Autowired
    public CarRentalRestService(CarService carService) {
        super();
        this.carService = carService;
    }

    @GetMapping("/cars")
    public List<Car> getListOfCars(){
        return carService.carToBeRented();
    }

    @PostMapping("/cars")
    public void addCar(@RequestBody Car car) throws Exception{
        carService.addCar(car);
    }

    @PutMapping(value = "/cars/{plateNumber}")
    public void rentOrGetBack(@PathVariable("plateNumber") String plateNumber,
                     @RequestParam(value="rent", required = true)boolean rent) throws CarNotFoundException {
        if(rent == true){
            carService.rent(plateNumber);
        } else {
            carService.getBack(plateNumber);
        }
    }
}
