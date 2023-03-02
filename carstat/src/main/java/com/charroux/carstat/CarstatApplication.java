package com.charroux.carstat;

import com.charroux.carstat.entity.Customer;
import com.charroux.carstat.entity.CustomerRepository;
import com.charroux.carstat.service.CustomerService;
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

	public static void main(String[] args) {
		SpringApplication.run(CarstatApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CustomerService customerService, CustomerRepository customerRepository) {
		return (args) -> {

			Customer customer = new Customer(3500);
			customerRepository.save(customer);
			Iterator<Customer> customers = customerRepository.findAll().iterator();
			while(customers.hasNext()){
				System.out.println(customers.next());
			}

			try {
				CarRentalServiceImpl carRentalService = new CarRentalServiceImpl();
				carRentalService.setCustomerService(customerService);

				Server server = ServerBuilder
						.forPort(8080)
						.addService(carRentalService).build();
				server.start();
				System.out.println("ok");
				server.awaitTermination();
			} catch (IOException e) {
				throw new RuntimeException(e);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		};
	};

}
