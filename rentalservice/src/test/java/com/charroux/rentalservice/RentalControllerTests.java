package com.charroux.rentalservice;

import com.charroux.rentalservice.agreements.RentalController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.graphql.test.tester.GraphQlTester;

//@GraphQlTest(RentalController.class)
public class RentalControllerTests {

    @Autowired
    private GraphQlTester graphQlTester;

    //@Test
    void shouldGetFirstRentalAgreement() {
        this.graphQlTester
                .documentName("rentalAgreementDetails")
                .variable("id", "rentalAgreement-1")
                .execute()
                .path("rentalAgreementById")
                .matchesJson("""
                    {
                        "id": "rentalAgreement-1",
                        "customer": {
                            "id": "customer-1",
                            "name": "Tintin"
                        },
                        "car": {
                            "plateNumber": "AA11BB",
                            "brand": "Ferrari"
                        }
                    }
                """);
    }
}