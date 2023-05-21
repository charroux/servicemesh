package com.charroux.customer.web;

import com.charroux.customer.entity.Customer;
import com.charroux.customer.entity.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerRestController {

    CustomerRepository customerRepository;

    @Autowired
    public CustomerRestController(CustomerRepository customerRepository) {
        super();
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customer/{id}")
    public Customer getCustomer(@PathVariable("id") long id) throws CustomerNotFoundException {
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isEmpty()) {
            throw new CustomerNotFoundException();
        }
        return customer.get();
    }


}
