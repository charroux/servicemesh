package com.charroux.rentalservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class RentalserviceApplication {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(final CorsRegistry registry) {
				registry.addMapping("/graphql/**")
						.allowedOrigins(CorsConfiguration.ALL)
						.allowedHeaders(CorsConfiguration.ALL)
						.allowedMethods(CorsConfiguration.ALL);
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(RentalserviceApplication.class, args);
	}

}
