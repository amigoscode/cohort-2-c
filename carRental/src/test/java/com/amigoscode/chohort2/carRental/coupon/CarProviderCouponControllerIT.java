package com.amigoscode.chohort2.carRental.coupon;

import com.amigoscode.chohort2.carRental.AbstractTestContainer;
import com.amigoscode.chohort2.carRental.carProvider.CarProvider;
import com.amigoscode.chohort2.carRental.carProvider.VM.CarProviderVM;
import com.amigoscode.chohort2.carRental.carProviderCoupon.*;
import com.amigoscode.chohort2.carRental.carProviderCoupon.VM.CarProviderCouponVM;
import com.amigoscode.chohort2.carRental.registration.VM.CarProviderRegistrationVM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static reactor.core.publisher.Mono.when;


public class CarProviderCouponControllerIT extends AbstractTestContainer {

    @Autowired
    private CarProviderCouponService carProviderCouponService;

    private CarProviderCouponDTO carProviderCouponDTO;
    private CarProviderCouponVM carProviderCouponVM;
    private CarProviderCoupon carProviderCoupon;
    @Autowired
    private WebTestClient webTestClient;
    private static final String API_URL = "api/v1/registrations/";


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
    }

    @Test
    public void shouldCreateCarProviderCoupon()  {
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
                .setCrNumber("123456789");

        carProviderRegistrationVM.setCarProviderVM(carProviderVM);

        // when - action or the behaviour that we are going test
        webTestClient
                .post()
                .uri(API_URL + "car-providers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(carProviderRegistrationVM), CarProviderRegistrationVM.class)
                .exchange()
                .expectStatus().isCreated();


    }
}
