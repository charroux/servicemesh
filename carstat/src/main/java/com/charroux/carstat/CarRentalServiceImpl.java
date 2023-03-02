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

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    /*
    @Override
    public StreamObserver<Car> bookingCars(StreamObserver<Invoice> responseObserver) {
        return new StreamObserver<Car>() {
            int price = 0;
            @Override
            public void onNext(Car car) {
                System.out.println("onNext of bookingCars receives a car: " + car);
                price = price + car.getPrice();
                responseObserver.onNext(Invoice.newBuilder().setPrice(price).build());
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
    public void waitForACar(GetCarsRequest request, StreamObserver<Car> responseObserver) {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Car car = Car.newBuilder().setPrice(100).setBrand("Ferrari").setPlateNumber("11AA22").build();
        responseObserver.onNext(car);
    }

    @Override
    public StreamObserver<Car> rentCars(StreamObserver<Invoice> responseObserver) {
        return new StreamObserver<Car>() {
            int price = 0;
            @Override
            public void onNext(Car car) {
                System.out.println("onNext of rentCars receives a car: " + car);
                price = price + car.getPrice();
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(Invoice.newBuilder().setPrice(price).build());
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public void getCars(GetCarsRequest request, StreamObserver<Car> responseObserver) {

        System.out.println("getCars call");

        for (int i = 1; i <= 5; i++) {
            Car car = Car.newBuilder()
                    .setPrice(100)
                    .setBrand("Ferrari")
                    .setPlateNumber("AA" + i)
                    .build();
            responseObserver.onNext(car);
        }
    }

     */

}
