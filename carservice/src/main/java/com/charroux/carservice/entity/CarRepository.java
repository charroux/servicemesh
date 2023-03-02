package com.charroux.carservice.entity;

import com.charroux.carservice.entity.Car;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRepository extends CrudRepository<Car, Long> {

    List<Car> findByPlateNumber(String plateNumber);
    List<Car> findByBrand(String brand);

}