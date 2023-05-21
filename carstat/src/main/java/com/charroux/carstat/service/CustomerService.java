package com.charroux.carstat.service;

import com.charroux.carstat.entity.CreditException;
import com.charroux.carstat.entity.Customer;

public interface CustomerService {

    void reserveCredit(long customerId, int amount) throws CustomerNotFoundException, CreditException;

    Customer findById(long customerId) throws CustomerNotFoundException;

}
