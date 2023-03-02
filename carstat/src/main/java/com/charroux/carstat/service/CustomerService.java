package com.charroux.carstat.service;

import com.charroux.carstat.entity.CreditException;

public interface CustomerService {

    void reserveCredit(long customerId, int amount) throws CustomerNotFoundException, CreditException;

}
