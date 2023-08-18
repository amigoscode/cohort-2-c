package com.amigoscode.chohort2.carRental.coupon;

import com.amigoscode.chohort2.carRental.AbstractTestContainer;
import com.amigoscode.chohort2.carRental.auth.LoginVM;
import com.amigoscode.chohort2.carRental.carProvider.CarProvider;
import com.amigoscode.chohort2.carRental.carProvider.VM.CarProviderVM;
import com.amigoscode.chohort2.carRental.carProviderCoupon.*;
import com.amigoscode.chohort2.carRental.carProviderCoupon.VM.CarProviderCouponVM;
import com.amigoscode.chohort2.carRental.registration.VM.CarProviderRegistrationVM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;


public class CarProviderCouponControllerIT extends AbstractTestContainer {

    @Autowired
    private CarProviderCouponService carProviderCouponService;

    private CarProviderCouponDTO carProviderCouponDTO;
    private CarProviderCouponVM carProviderCouponVM;
    private CarProviderCoupon carProviderCoupon;
    @Autowired
    private WebTestClient webTestClient;
    private static final String CAR_PROVIDER_REGISTRATION_URL = "api/v1/registrations/car-providers";
    private static final String LOGIN_URL = "api/v1/auth/login";

    private static String JWT = "";




    @BeforeEach
    void tearUp() {
        carProviderCouponVM = new CarProviderCouponVM()
                .setCouponCode("thayscr1000")
                .setStartDate(LocalDate.of(2023, 11, 3))
                .setEndDate(LocalDate.of(2023, 12, 2))
                .setNumOfUsePerUser(1)
                .setIsAvailable(true);

        carProviderCoupon = new CarProviderCoupon()
                .setId(1L)
                .setCarProviderId(1L)
                .setCarProvider(new CarProvider().setId(1L)
                        .setName("HILLUX")
                        .setCrNumber("1234567"))
                .setCouponCode("thayscr1000")
                .setStartDate(LocalDate.of(2023, 11, 3))
                .setEndDate(LocalDate.of(2023, 12, 2))
                .setNumOfUsePerUser(1)
                .setIsAvailable(true);

        // given precondition or setup
        CarProviderRegistrationVM carProviderRegistrationVM = (CarProviderRegistrationVM) new CarProviderRegistrationVM()
                .setUsername("carProvider")
                .setFirstName("esmaeeil")
                .setLastName("enani")
                .setEmail("carProvider@gmail.com")
                .setNin("12345678988")
                .setPassword("123456789");

        CarProviderVM carProviderVM = new CarProviderVM()
                .setName("car provider test")
                .setCrNumber("123456798");

        carProviderRegistrationVM.setCarProviderVM(carProviderVM);


        webTestClient
                .post()
                .uri(CAR_PROVIDER_REGISTRATION_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(carProviderRegistrationVM), CarProviderRegistrationVM.class)
                .exchange()
                .expectStatus().isCreated();


        LoginVM loginVM = new LoginVM(carProviderRegistrationVM.getUsername(), carProviderRegistrationVM.getPassword());

        JWT = webTestClient
                .post()
                .uri(LOGIN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(loginVM), LoginVM.class)
                .exchange()
                .expectBody(new ParameterizedTypeReference<String>() {
                })
                .returnResult()
                .getResponseBody();


    }

    @Test
    public void shouldCreateCarProviderCoupon() {


        // when - action or the behaviour that we are going test

//        webTestClient
//                .post()
//                .uri(LOGIN_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .header(HttpHeaders.AUTHORIZATION,"Bearer "+JWT)
//                .body(Mono.just(loginVM), LoginVM.class)
//                .exchange()
//                .expectBody(new ParameterizedTypeReference<String>() {
//                })
//                .returnResult()
//                .getResponseBody();




    }
}
