package com.charroux.rentalservice.cars;

import java.util.Arrays;
import java.util.List;

public record Car (String plateNumber, String brand, int price) {

    private static List<Car> cars = Arrays.asList(
            new Car("AA11BB", "Ferrari", 1000),
            new Car("CC22FF", "Lamborghini", 2000)
    );

    public static Car getById(String id) {
        return cars.stream()
                .filter(car -> car.plateNumber().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static Iterable<Car> findAll() {
        return cars;
    }

    public static Car save(Car car) {
        cars.add(car);
        return car;
    }
}