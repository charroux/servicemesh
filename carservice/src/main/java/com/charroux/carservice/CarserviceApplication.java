package com.charroux.carservice;

import com.charroux.carservice.entity.Car;
import com.charroux.carservice.entity.CarRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.ForwardedHeaderFilter;

@SpringBootApplication
public class CarserviceApplication {

	@Bean
	ForwardedHeaderFilter forwardedHeaderFilter() {
		return new ForwardedHeaderFilter();
	}

	public static void main(String[] args) {
		SpringApplication.run(CarserviceApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CarRepository carRepository) {
		return (args) -> {
			Car car = new Car("11AA22", "Ferrari", 1000);
			carRepository.save(car);
			car = new Car("22BB33", "Porshe", 1000);
			carRepository.save(car);
			car = new Car("33CC44", "Lamborghini", 1000);
			carRepository.save(car);
			car = new Car("44DD55", "Mac Laren", 1000);
			carRepository.save(car);
		};
	};

}
