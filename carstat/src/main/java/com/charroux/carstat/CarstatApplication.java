package com.charroux.carstat;

import com.charroux.carstat.entity.Customer;
import com.charroux.carstat.entity.CustomerRepository;
import com.charroux.carstat.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.Iterator;

import io.grpc.Server;
import io.grpc.ServerBuilder;

@SpringBootApplication
public class CarstatApplication {

	Logger log = LoggerFactory.getLogger(CarstatApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CarstatApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CustomerService customerService, CustomerRepository customerRepository) {
		return (args) -> {

			Customer customer = new Customer(3500, "Tintin");
			customerRepository.save(customer);

			customer = new Customer(2000, "Haddock");
			customerRepository.save(customer);

			try {
				CarRentalServiceImpl carRentalService = new CarRentalServiceImpl();
				carRentalService.setCustomerService(customerService);

				Server server = ServerBuilder
						.forPort(8080)
						.addService(carRentalService).build();
				server.start();

				log.info("Grpc server started");

				server.awaitTermination();
			} catch (IOException e) {
				throw new RuntimeException(e);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		};
	};

}
