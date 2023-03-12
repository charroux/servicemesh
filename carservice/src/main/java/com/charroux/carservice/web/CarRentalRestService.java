package com.charroux.carservice.web;

import com.charroux.carservice.entity.Car;
import com.charroux.carservice.entity.RentalAgreement;
import com.charroux.carservice.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import io.grpc.carservice.CarRentalServiceGrpc;
//import io.grpc.carservice.Invoice;
//import io.grpc.carservice.Car;

import java.util.List;

@RestController
public class CarRentalRestService {

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

}
