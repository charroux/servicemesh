package com.charroux.carstat.service;

import com.charroux.carstat.entity.CreditException;
import com.charroux.carstat.entity.Customer;
import com.charroux.carstat.entity.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{

    CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public void reserveCredit(long customerId, int amount) throws CustomerNotFoundException, CreditException {
        Customer customer = customerRepository.findById(customerId).get();
        if(customer == null){
            throw new CustomerNotFoundException();
        }
        customer.reserveCredit(amount);
        System.out.println("Save=" + customer.getCredit());
        customerRepository.save(customer);
    }

    @Override
    public Customer findById(long customerId) throws CustomerNotFoundException {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isEmpty()) {
            throw new CustomerNotFoundException();
        }
        return customer.get();
    }
}
