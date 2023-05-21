package com.charroux.carservice.service;

import com.charroux.carservice.entity.Car;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RentalServiceTests {

    @MockBean
    RentalServiceImpl rentalService;

    @Test
    void getAllCarsToBeRented() {
        Car car = new Car("11AA22", "Ferrari", 1000);
        doNothing().when(rentalService).addCar(car);
        when(rentalService.carsToBeRented()).thenReturn(Collections.singletonList(car));
    }

}
