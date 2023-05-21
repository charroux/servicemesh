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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;

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

    /*@BatchMapping
    public Map<RentalAgreement, Collection<Car>> cars(List<RentalAgreement> agreements) {
        log.info("Fetching cars for agreemments {} ", agreements);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(agreements,headers);
        ResponseEntity<Map<RentalAgreement, Collection<Car>>> response = restTemplate.exchange(carServiceURL + "/carsForAgreements", HttpMethod.POST, requestEntity,new ParameterizedTypeReference<Map<RentalAgreement, Collection<Car>>>() {});
        return response.getBody();
    }*/

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

 /*   @QueryMapping
    public Iterable<Rental> rentalAgreements(DataFetchingEnvironment environment) {
        DataFetchingFieldSelectionSet s = environment.getSelectionSet();
        List<Specification<Rental>> specifications = new ArrayList<>();
        if (s.contains("cars") && !s.contains("customer")) {
            return this.getAllRentals();
        } else if (s.contains("cars") && s.contains("customer")) {
            Iterable<Rental> rentals = this.getAllRentals();
            Iterator<Rental> iterator = rentals.iterator();
            while (iterator.hasNext()) {
                iterator.next();
                customerController.customerByID()
            }
            return this.getAllRentals();
        }
        return null;
    }*/

    /*@QueryMapping
    public RentalAgreement rentalAgreementByCustomerId(@Argument int customerId, DataFetchingEnvironment environment) {
        DataFetchingFieldSelectionSet s = environment.getSelectionSet();
        RentalAgreement rentalAgreement = this.getRentalByCustomerId(customerId);
        if (s.contains("cars") && !s.contains("customer")) {
            return rentalAgreement;
        } else if (s.contains("cars") && s.contains("customer")) {
            Customer customer = customerController.customerById(customerId);
            rentalAgreement.setCustomer(customer);
            return rentalAgreement;
        }
        return null;
    }*/


}
