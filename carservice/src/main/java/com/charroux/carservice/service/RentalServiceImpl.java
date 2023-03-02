package com.charroux.carservice.service;

import com.charroux.carservice.entity.Car;
import com.charroux.carservice.entity.CarRepository;
import com.charroux.carservice.entity.RentalAgreement;
import com.charroux.carservice.entity.RentalAgreementRepository;
import com.charroux.carservice.web.CarNotFoundException;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import io.grpc.carservice.CarRentalServiceGrpc;
import io.grpc.carservice.CreditApplication;
import io.grpc.carservice.Agreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Service
public class RentalServiceImpl implements RentalService {

    CarRepository carRepository;
    RentalAgreementRepository rentalAgreementRepository;

    @Autowired
    public RentalServiceImpl(CarRepository carRepository, RentalAgreementRepository rentalAgreementRepository) {
        super();
        this.carRepository = carRepository;
        this.rentalAgreementRepository = rentalAgreementRepository;
    }

    class AgreementObserver extends Thread implements StreamObserver<Agreement> {

        CountDownLatch countDownLatch;
        RentalAgreement rentalAgreement;

        public AgreementObserver(CountDownLatch countDownLatch, RentalAgreement rentalAgreement){
            System.out.println("construc");
            this.countDownLatch = countDownLatch;
            this.rentalAgreement = rentalAgreement;
        }

        @Override
        public void onNext(Agreement agreement) {
            String creditReservedEvent = agreement.getCreditReservedEvent();
            switch (creditReservedEvent) {
                case "CREDIT_RESERVED" :
                    rentalAgreement.setState(RentalAgreement.State.CREDIT_RESERVED);
                    break;
                default:
                    rentalAgreement.setState(RentalAgreement.State.CREDIT_REJECTED);
            }
            System.out.println("onNext client receives: " + agreement);
        }

        @Override
        public void onError(Throwable t) {

        }

        @Override
        public void onCompleted() {
            System.out.println("on completed");
            countDownLatch.countDown();
        }

        @Override
        public void run() {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("fin run");
        }
    }


    @Override
    public RentalAgreement rent(long customerId, int numberOfCars) throws CarNotFoundException {

        List<Car> cars = this.carsToBeRented();

        if(cars.size() < numberOfCars){
            throw new CarNotFoundException();
        }

        String host = System.getenv("ECHO_SERVICE_HOST");
        String port = System.getenv("ECHO_SERVICE_PORT");
        final ManagedChannel channel = ManagedChannelBuilder.forTarget(host + ":" + port)
                .usePlaintext()
                .build();

        CarRentalServiceGrpc.CarRentalServiceStub nonBlockingStub = CarRentalServiceGrpc.newStub(channel);

        CountDownLatch countDownLatch = new CountDownLatch(1);

        RentalAgreement rentalAgreement = new RentalAgreement(customerId, RentalAgreement.State.PENDING);
        rentalAgreement.setState(RentalAgreement.State.PENDING);
        rentalAgreementRepository.save(rentalAgreement);
        System.out.println("debut=" + rentalAgreement.hashCode() + " " + rentalAgreement);

        AgreementObserver agreementObserver = new AgreementObserver(countDownLatch, rentalAgreement);

        agreementObserver.start();

        StreamObserver<CreditApplication> carsObserver = nonBlockingStub.rentCars(agreementObserver);

        Car car;
        for (int i=0; i<numberOfCars; i++) {
            car = cars.get(i);
            rentalAgreement.addCar(car);
            System.out.println(car);
            CreditApplication creditApplication = CreditApplication.newBuilder().setCustomerId(customerId).setPrice(car.getPrice()).build();
            carsObserver.onNext(creditApplication);
        }

        carsObserver.onCompleted();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        channel.shutdown();

        if(rentalAgreement.getState() == RentalAgreement.State.CREDIT_REJECTED) {
            System.out.println("rejet");
            rentalAgreement.getCars().stream().peek(aCar -> aCar.setRentalAgreement(null));
            rentalAgreement.setCars(new ArrayList<Car>());
        } else {
            System.out.println("pas rejet");
        }

        System.out.println("fin=" + rentalAgreement.hashCode() + " " + rentalAgreement);

        rentalAgreementRepository.save(rentalAgreement);

        System.out.println("it1:");
        Iterator<RentalAgreement> iterator = rentalAgreementRepository.findAll().iterator();
        while ((iterator.hasNext())){
            System.out.println(iterator.next());
        }

        System.out.println("it2:");
        Iterator<RentalAgreement> iterator1 = this.getAgreements().iterator();
        while ((iterator1.hasNext())){
            System.out.println(iterator1.next());
        }

        return rentalAgreement;
    }

    @Override
    public List<Car> carsToBeRented() {
        List<Car> cars = new ArrayList<Car>();
        Iterator<Car> it = carRepository.findAll().iterator();
        while (it.hasNext()){
            Car car = it.next();
            if(car.getRentalAgreement() == null){
                cars.add(car);
            }
        }
        return cars;
    }

    @Override
    public List<RentalAgreement> getAgreements(){
        List<RentalAgreement> result = new ArrayList<RentalAgreement>();
        Iterator<RentalAgreement> it = rentalAgreementRepository.findAll().iterator();
        while (it.hasNext()) {
            result.add(it.next());
        }
        return result;
    }

}
