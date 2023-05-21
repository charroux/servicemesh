package com.charroux.carstat.entity;

import com.charroux.carstat.CarstatApplication;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
public class Customer {

    Logger log = LoggerFactory.getLogger(Customer.class);

    long id;
    String name;
    int credit;

    public Customer() {}

    public Customer(int credit, String name) {
        this.credit = credit;
        this.name = name;
    }

    public void reserveCredit(int amount) throws CreditException {
        if(credit >= amount){
            credit = credit - amount;
            log.info("credit reservation: " + amount + " credit = " +credit);
        } else {
            log.info("credit reservation: " + amount + " not enough credit");
            throw new CreditException();
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", credit=" + credit +
                '}';
    }
}
