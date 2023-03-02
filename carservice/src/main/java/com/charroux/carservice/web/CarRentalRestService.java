package com.charroux.carservice.web;

import com.charroux.carservice.entity.Car;
import com.charroux.carservice.entity.RentalAgreement;
import com.charroux.carservice.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import io.grpc.carservice.CarRentalServiceGrpc;
//import io.grpc.carservice.Invoice;
//import io.grpc.carservice.Car;

import java.util.List;

@RestController
public class CarRentalRestService {

 /*   class InvoiceObserver extends Thread implements StreamObserver<Invoice> {

        CountDownLatch countDownLatch;

        public InvoiceObserver(CountDownLatch countDownLatch){
            System.out.println("construc");
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void onNext(Invoice value) {
            System.out.println("onNext client receives: " + value);
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

    @GetMapping("/hello")
    public String essai(){

        System.out.println("ooookkkkkkkkkkkk");

        String host = System.getenv("ECHO_SERVICE_HOST");
        String port = System.getenv("ECHO_SERVICE_PORT");
        final ManagedChannel channel = ManagedChannelBuilder.forTarget(host + ":" + port)
                .usePlaintext()
                .build();

        CarRentalServiceGrpc.CarRentalServiceStub nonBlockingStub = CarRentalServiceGrpc.newStub(channel);

        CountDownLatch countDownLatch = new CountDownLatch(1);

        InvoiceObserver invoiceObserver = new InvoiceObserver(countDownLatch);

        invoiceObserver.start();

        StreamObserver<Car> carsObserver = nonBlockingStub.bookingCars(invoiceObserver);

        Car ferrari = Car.newBuilder().setPlateNumber("11AA22").setPrice(100).setBrand("Ferrari").build();
        carsObserver.onNext(ferrari);

        Car porshe = Car.newBuilder().setPlateNumber("33BB44").setPrice(200).setBrand("porshe").build();
        carsObserver.onNext(porshe);

        carsObserver.onCompleted();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        channel.shutdown();

        return "ookkkiiiki";
    }*/

    RentalService rentalService;

    @Autowired
    public CarRentalRestService(RentalService rentalService) {
        super();
        this.rentalService = rentalService;
    }

    @GetMapping("/cars")
    public List<Car> getListOfCars(){
        return rentalService.carsToBeRented();
    }

    @PostMapping("/cars")
    public ResponseEntity<RentCarsResponse>  rentCars(@RequestBody RentCarsRequest rentCarsRequest) throws Exception{
        RentalAgreement rentalAgreement = rentalService.rent(
                rentCarsRequest.getCustomerId(),
                rentCarsRequest.getNumberOfCars());
        return new ResponseEntity<>(
                new RentCarsResponse(
                        rentalAgreement.getCustomerId(), rentalAgreement.getId(), rentalAgreement.getState().name()),
                HttpStatus.OK);
    }

    @GetMapping("/agreements")
    public List<RentalAgreement> getAgreements(){
        return rentalService.getAgreements();
    }

}
