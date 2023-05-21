package com.charroux.carstat;

import com.charroux.carstat.entity.CreditException;
import com.charroux.carstat.service.CustomerNotFoundException;
import com.charroux.carstat.service.CustomerService;
import io.grpc.carservice.*;
import io.grpc.stub.StreamObserver;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

public class CarRentalServiceImpl extends CarRentalServiceGrpc.CarRentalServiceImplBase {

    CustomerService customerService;

    @Override
    public StreamObserver<CreditApplication> rentCars(StreamObserver<Agreement> responseObserver) {

        return new StreamObserver<CreditApplication>() {
            @Override
            public void onNext(CreditApplication creditApplication) {
                try{
                    System.out.println(creditApplication.getPrice());
                    customerService.reserveCredit(
                            creditApplication.getCustomerId(),
                            creditApplication.getPrice()
                    );
                    responseObserver.onNext(Agreement.newBuilder().setCreditReservedEvent("CREDIT_RESERVED").build());
                } catch (CreditException e) {
                    System.out.println("CreditException");
                    responseObserver.onNext(Agreement.newBuilder().setCreditReservedEvent("CREDIT_REJECTED").build());
                } catch (CustomerNotFoundException e) {
                    responseObserver.onNext(Agreement.newBuilder().setCreditReservedEvent("CREDIT_REJECTED").build());
                }
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public void getCustomer(CustomerID request, StreamObserver<Customer> responseObserver) {
        com.charroux.carstat.entity.Customer customer = null;
        try {
            customer = customerService.findById(request.getCustomerId());
        } catch (CustomerNotFoundException e) {
            responseObserver.onError(e);
        }
        responseObserver.onNext(Customer.newBuilder().setCredit(customer.getCredit()).setName(customer.getName()).build());
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

}
