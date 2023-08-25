package com.amigoscode.chohort2.carRental.auth;

import com.amigoscode.chohort2.carRental.AbstractTestContainer;
import com.amigoscode.chohort2.carRental.carProviderCoupon.VM.CarProviderCouponAvailabilityVM;
import com.amigoscode.chohort2.carRental.driverLicense.VM.DriverLicenseVM;
import com.amigoscode.chohort2.carRental.registration.RegistrationService;
import com.amigoscode.chohort2.carRental.registration.VM.ClientRegistrationVM;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
class AuthControllerIT extends AbstractTestContainer {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private RegistrationService registrationService;

    private static final String API_URL = "api/v1/auth/";



    @Test
    void givenLoginInfo_whenPreformLogin_thenReturnJWT() {
        // given precondition or setup
        ClientRegistrationVM clientRegistrationVM = (ClientRegistrationVM) new ClientRegistrationVM()
                .setUsername("esmaeeilll")
                .setFirstName("esmaeeil")
                .setLastName("enaniii")
                .setEmail("enaniii@gmail.com")
                .setNin("123456781")
                .setPassword("123456789");

        DriverLicenseVM driverLicenseVM = new DriverLicenseVM()
                .setDriverLicenseNumber("123456781")
                .setIssuedIn(1L)
                .setIssuedAt(LocalDate.now().minusYears(3))
                .setExpiredDate(LocalDate.now().plusYears(3));

        clientRegistrationVM.setDriverLicense(driverLicenseVM);
        registrationService.registration(clientRegistrationVM);

        // when - action or the behaviour that we are going test
       String jwt = webTestClient
                .post()
                .uri(API_URL + "login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(new LoginVM(clientRegistrationVM.getUsername(),"123456789")), LoginVM.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();


        // then verify the output
        assertThat(jwt).isNotBlank();

    }


}