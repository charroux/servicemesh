package com.charroux.carservice.service;

import com.charroux.carservice.persistence.CarJPA;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRepository extends CrudRepository<CarJPA, Long> {

    List<CarJPA> findByPlateNumber(String plateNumber);

}