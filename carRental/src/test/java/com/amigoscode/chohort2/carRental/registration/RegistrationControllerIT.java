package com.amigoscode.chohort2.carRental.registration;

import com.amigoscode.chohort2.carRental.AbstractTestContainer;
import com.amigoscode.chohort2.carRental.driverLicense.VM.DriverLicenseVM;
import com.amigoscode.chohort2.carRental.registration.VM.ClientRegistrationVM;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RegistrationControllerIT extends AbstractTestContainer {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private RegistrationService registrationService;


    private static final String API_URL = "api/v1/registrations/";


    @Test
    void given_when_then() {
        // given precondition or setup
        ClientRegistrationVM clientRegistrationVM = new ClientRegistrationVM()
                .setUsername("esmaeeil")
                .setFirstName("esmaeeil")
                .setLastName("enani")
                .setEmail("enani@gmail.com")
                .setNin("123456789")
                .setPassword("123456789");

        DriverLicenseVM driverLicenseVM = new DriverLicenseVM()
                .setDriverLicenseNumber("123456789")
                .setIssuedIn(1L)
                .setIssuedAt(LocalDate.now().minusYears(3))
                .setExpiredDate(LocalDate.now().plusYears(3));

        clientRegistrationVM.setDriverLicense(driverLicenseVM);


        // when - action or the behaviour that we are going test
        webTestClient
                .post()
                .uri(API_URL+"clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(clientRegistrationVM), ClientRegistrationVM.class)
                .exchange()
                .expectStatus().isCreated();


        // then verify the output

    }
}