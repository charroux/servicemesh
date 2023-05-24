package com.charroux.rentalservice.cars;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@RestController
public class CarsController {

    Logger log = LoggerFactory.getLogger(CarsController.class);

    @Value("${carServiceURL}")
    String carServiceURL;

    @QueryMapping
    public Iterable<Car> cars() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Car[]> response = restTemplate.getForEntity(
            carServiceURL + "/cars",
            Car[].class);
        Car[] cars = response.getBody();
        return Arrays.asList(cars);
    }

    @QueryMapping
    public Car carById(@Argument String plateNumber) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Car> response = restTemplate.getForEntity(
                carServiceURL + "/cars/" + plateNumber,
                Car.class);
        return response.getBody();
    }

    @QueryMapping
    public Iterable<Car> carsWithFilter(@Argument CarFilter filter) {
        Specification<Car> spec = null;
        if (filter.price() != null)
            spec = byPrice(filter.price());
        if (spec != null) {
            return cars();
        }
        else
            return cars();
    }

    private Specification<Car> byPrice(FilterField filterField) {
        return (root, query, builder) -> filterField
                .generateCriteria(builder, root.get("price"));
    }

    @SubscriptionMapping
    public Flux<Car> notifyNewCarPrice(@Argument String plateNumber) {
        log.info("Subscription");

        //return Flux.interval(Duration.ofSeconds(1)).map(num -> new Car());
        return Flux.fromStream(
            Stream.generate(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Car car = this.carById(plateNumber);
                int price = (int)Math.random();
                log.info("price = " + price);
                car.setPrice(price);
                return car;
            }));
    }

}
