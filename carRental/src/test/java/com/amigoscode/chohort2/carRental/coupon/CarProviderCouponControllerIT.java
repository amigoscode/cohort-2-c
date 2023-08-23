package com.amigoscode.chohort2.carRental.coupon;

import com.amigoscode.chohort2.carRental.AbstractTestContainer;
import com.amigoscode.chohort2.carRental.auth.LoginVM;

import com.amigoscode.chohort2.carRental.carProvider.VM.CarProviderVM;
import com.amigoscode.chohort2.carRental.carProviderCoupon.*;
import com.amigoscode.chohort2.carRental.carProviderCoupon.VM.CarProviderCouponAvailabilityVM;
import com.amigoscode.chohort2.carRental.carProviderCoupon.VM.CarProviderCouponVM;
import com.amigoscode.chohort2.carRental.registration.RegistrationService;
import com.amigoscode.chohort2.carRental.registration.VM.CarProviderRegistrationVM;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Optional;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


public class CarProviderCouponControllerIT extends AbstractTestContainer {

    @Autowired
    private CarProviderCouponRepository carProviderCouponRepository;
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private  CarProviderCouponService carProviderCouponService;
    private CarProviderCouponVM carProviderCouponVM;
    private CarProviderCouponAvailabilityVM updatedItem;
    protected static final Faker FAKER = new Faker();
    private CarProviderRegistrationVM carProviderRegistrationVM;
    private static final String LOGIN_URL = "api/v1/auth/login";

    private static final String CAR_PROVIDER_COUPON_URL = "api/v1/car-provider-coupons/";

    private static String JWT;


    @BeforeEach
    void tearUp() {
        carProviderCouponVM = new CarProviderCouponVM()
                .setCouponCode("thayscr1000")
                .setStartDate(LocalDate.of(2023, 11, 3))
                .setEndDate(LocalDate.of(2023, 12, 2))
                .setNumOfUsePerUser(1)
                .setIsAvailable(true);
        updatedItem = new CarProviderCouponAvailabilityVM()
                .setIsAvailable(false);

        // given precondition or setup

        carProviderRegistrationVM = (CarProviderRegistrationVM) new CarProviderRegistrationVM()
                .setUsername(FAKER.name().username())
                .setFirstName(FAKER.name().firstName())
                .setLastName(FAKER.name().lastName())
                .setEmail(FAKER.internet().emailAddress())
                .setNin(FAKER.number().digits(10))
                .setPassword(FAKER.number().digits(8));

        CarProviderVM carProviderVM = new CarProviderVM()
                .setName(FAKER.funnyName().name())
                .setCrNumber(FAKER.number().digits(9));

        carProviderRegistrationVM.setCarProviderVM(carProviderVM);
        registrationService.registration(carProviderRegistrationVM);
        JWT = webTestClient
                .post()
                .uri(LOGIN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(new LoginVM(carProviderRegistrationVM.getUsername(), carProviderRegistrationVM.getPassword())), LoginVM.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();
    }

    @Test
    public void shouldCreateCarProviderCoupon() {

        webTestClient
                .post()
                .uri(CAR_PROVIDER_COUPON_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT)
                .body(Mono.just(carProviderCouponVM), CarProviderCouponVM.class)
                .exchange()
                .expectStatus().isAccepted()
                .expectBody(new ParameterizedTypeReference<CarProviderCouponDTO>() {
                })
                .returnResult()
                .getResponseBody();

        assertThat(carProviderCouponRepository.findByCouponCode(carProviderCouponVM.getCouponCode())).isPresent();
    }

    @Test
    public void shouldUpdateCarProviderCouponAvailability() {
        CarProviderCouponDTO couponDTO = carProviderCouponService.save(carProviderCouponVM);

      CarProviderCouponDTO update=  webTestClient
                .put()
                .uri(CAR_PROVIDER_COUPON_URL  +  couponDTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT)
                .body(Mono.just(updatedItem), CarProviderCouponAvailabilityVM.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<CarProviderCouponDTO>() {
                })
                .returnResult()
                .getResponseBody();

        assertThat(update.getIsAvailable()).isFalse();
    }
}
