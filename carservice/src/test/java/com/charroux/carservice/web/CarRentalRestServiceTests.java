package com.charroux.carservice.web;

import com.charroux.carservice.entity.Car;
import com.charroux.carservice.entity.RentalAgreement;
import com.charroux.carservice.service.RentalServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.http.MediaType;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CarRentalRestServiceTests {

    @MockBean
    RentalServiceImpl rentalService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void testZeroVoiture() throws Exception {
        mockMvc.perform(get("/cars")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void rentCar() throws Exception {
        /*Car car = new Car("11AA22", "Ferrari", 1000);
        doNothing().when(rentalService).addCar(car);
        RentalAgreement rentalAgreement = new RentalAgreement(1, RentalAgreement.State.CREDIT_RESERVED);
        rentalAgreement.addCar(car);
        when(rentalService.rent(1, 1)).thenReturn(rentalAgreement);
        mockMvc.perform(post("/cars")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"customerId\": 1, \"numberOfCars\": 2 }")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());*/
    }

}
