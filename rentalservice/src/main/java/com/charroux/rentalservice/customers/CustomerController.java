package com.charroux.rentalservice.customers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CustomerController {

    Logger log = LoggerFactory.getLogger(CustomerController.class);

    @Value("${carServiceURL}")
    String carServiceURL;

    @MutationMapping
    public RentCarsResponse rentCars(@Argument RentCarsRequest rentCarsRequest) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(rentCarsRequest,headers);
        ResponseEntity<RentCarsResponse> response = restTemplate.exchange(carServiceURL + "/cars", HttpMethod.POST, requestEntity,new ParameterizedTypeReference<RentCarsResponse>() {});
        return response.getBody();
    }

}
