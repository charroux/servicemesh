package com.charroux.rentalservice.agreements;

import com.charroux.rentalservice.cars.*;
import com.charroux.rentalservice.customers.Customer;
import com.charroux.rentalservice.customers.CustomerController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;

//@CrossOrigin(origins = "http://localhost:8080/graphql", maxAge = 3600)
@RestController
public class RentalController {

    Logger log = LoggerFactory.getLogger(RentalController.class);

    @Value("${carServiceURL}")
    String carServiceURL;

    @Value("${customerServiceURL}")
    String customerServiceURL;

    @Autowired
    CustomerController customerController;

    @Autowired
    CarsController carsController;

    private Iterable<RentalAgreement> getAllRentals() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RentalAgreement[]> response = restTemplate.getForEntity(
                carServiceURL + "/agreements",
                RentalAgreement[].class);
        RentalAgreement[] rentalAgreements = response.getBody();
        return Arrays.asList(rentalAgreements);
    }

    private RentalAgreement getRentalByCustomerId(int customerId) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RentalAgreement> response = restTemplate.getForEntity(
                carServiceURL + "/agreement?customerId=" + customerId,
                RentalAgreement.class);
        return response.getBody();
    }

    @QueryMapping()
    public Iterable<RentalAgreement> rentalAgreements() {
        log.info("Fetching all agreements...");
        return this.getAllRentals();
    }

    @SchemaMapping
    public Collection<Car> cars(RentalAgreement rentalAgreement) {
        log.info("Fetching cars for agreemment {} ", rentalAgreement);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(rentalAgreement,headers);
        ResponseEntity<Collection<Car>> response = restTemplate.exchange(carServiceURL + "/carsForAgreement", HttpMethod.POST, requestEntity,new ParameterizedTypeReference<Collection<Car>>() {});
        return response.getBody();
    }

    @SchemaMapping
    public Customer customer(RentalAgreement rentalAgreement) {
        log.info("Fetching customer for agreemment {} ", rentalAgreement);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Customer> response = restTemplate.getForEntity(
                customerServiceURL + "/customer/" + rentalAgreement.getCustomerId(),
                Customer.class);
        return response.getBody();
    }

    @QueryMapping()
    public RentalAgreement rentalAgreementByCustomerId(@Argument int customerId) {
        log.info("Fetching retal agreement for customer id: " + customerId);
        return this.getRentalByCustomerId(customerId);
    }

}
