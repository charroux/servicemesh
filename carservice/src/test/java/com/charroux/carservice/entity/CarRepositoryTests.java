package com.charroux.carservice.entity;

import org.hibernate.query.sqm.mutation.internal.cte.CteInsertStrategy;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.times;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class CarRepositoryTests {

    @MockBean
    private CarRepository carRepository;

    @Test
    public void cerRepository() {
        Car car = new Car("AA11BB", "Ferrari", 1000);
        when(carRepository.save(car)).thenReturn(car);
        when(carRepository.findAll()).thenReturn(Collections.singletonList(car));
    }

    @Test
    void aSingleCar(){
        Car car = new Car("AA11BB", "Ferrari", 1000);
        when(carRepository.save(car)).thenReturn(car);
        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));
    }

    @Test
    void supprimerVoiture(){
        Car car = new Car("AA11BB", "Ferrari", 1000);
        when(carRepository.save(car)).thenReturn(car);
        carRepository.delete(car);
        Mockito.verify(carRepository, times(1)).delete(car);
    }

}
