package com.charroux.rentalservice;

import com.charroux.rentalservice.cars.Car;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.List;

//@GraphQlTest(CarController.class)
public class CarControllerTests {

    @Autowired
    private GraphQlTester graphQlTester;

    //@Test
    void carDetails() {
        this.graphQlTester
                .documentName("carDetails")
                .variable("plateNumber", "AA11BB")
                .execute()
                .path("carById")
                .matchesJson("""
                            {
                                "plateNumber": "AA11BB",
                                "brand": "Ferrari",
                                "price": 1000
                            }
                        """);
    }

    //@Test
    void allCarsWithPlateNumberOnly() {
        List<Car> cars = this.graphQlTester
                .documentName("allCars")
                .execute()
                .path("cars")
                .entityList(Car.class)
                .get();
        Assertions.assertTrue(cars.size() == 2);
        Assertions.assertNotNull(cars.get(0).plateNumber());
        Assertions.assertNull(cars.get(0).brand());
    }

    //@Test
    void allCarsWithPriceGreaterThan1000() {
        List<Car> cars = this.graphQlTester
                .documentName("allCarsWithPriceGreaterThan1000")
                .execute()
                .path("carsWithFilter")
                .entityList(Car.class)
                .get();
        Assertions.assertTrue(cars.size() == 2);
        //Assertions.assertNotNull(cars.get(0).plateNumber());
        //Assertions.assertNull(cars.get(0).brand());
    }
}