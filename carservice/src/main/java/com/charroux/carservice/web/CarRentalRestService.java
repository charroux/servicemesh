package com.charroux.carservice.web;

import com.charroux.carservice.entity.Car;
import com.charroux.carservice.entity.RentalAgreement;
import com.charroux.carservice.service.RentalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import io.grpc.carservice.CarRentalServiceGrpc;
//import io.grpc.carservice.Invoice;
//import io.grpc.carservice.Car;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class CarRentalRestService {

    Logger logger = LoggerFactory.getLogger(CarRentalRestService.class);

    RentalService rentalService;

    @Autowired
    public CarRentalRestService(RentalService rentalService) {
        super();
        this.rentalService = rentalService;
    }

    @GetMapping("/cars")
    public List<Car> getListOfCars(){
        return rentalService.carsToBeRented();
    }

    @GetMapping("/cars/{plateNumber}")
    public Car getCarByPlateNumber(@PathVariable("plateNumber") String plateNumber) throws CarNotFoundException {
        return rentalService.getCar(plateNumber);
    }

    @PostMapping("/carsForAgreement")
    public Collection<Car> carsForAgreement(@RequestBody RentalAgreement rentalAgreement) {
        return rentalService.getCars(rentalAgreement);
    }

   /* @PostMapping("/carsForAgreements")
    public Map<RentalAgreement, Collection<Car>> carsForAgreements(@RequestBody List<RentalAgreement> agreements) {
        return agreements.stream()
                .collect(
                        Collectors.toMap(
                                Function.identity(),
                                rentalAgreement ->
                                        rentalService.getCars(rentalAgreement)));
    }*/

    @PostMapping("/cars")
    public ResponseEntity<RentCarsResponse>  rentCars(@RequestBody RentCarsRequest rentCarsRequest) throws Exception{
        RentalAgreement rentalAgreement = rentalService.rent(
                rentCarsRequest.getCustomerId(),
                rentCarsRequest.getNumberOfCars());
        return new ResponseEntity<>(
                new RentCarsResponse(
                        rentalAgreement.getCustomerId(), rentalAgreement.getId(), rentalAgreement.getState().name()),
                HttpStatus.OK);
    }

    @GetMapping("/agreements")
    public List<RentalAgreement> getAgreements(){
        return rentalService.getAgreements();
    }

    @GetMapping("/agreement")
    public RentalAgreement getAgreement(@RequestParam(value = "customerId", required = true) int customerId) throws CustomerNotFoundException {
        return rentalService.getAgreement(customerId);
    }


}
