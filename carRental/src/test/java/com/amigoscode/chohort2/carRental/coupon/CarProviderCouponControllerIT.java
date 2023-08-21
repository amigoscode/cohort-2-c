package com.amigoscode.chohort2.carRental.coupon;

import com.amigoscode.chohort2.carRental.AbstractTestContainer;
import com.amigoscode.chohort2.carRental.auth.LoginVM;

import com.amigoscode.chohort2.carRental.carProvider.VM.CarProviderVM;
import com.amigoscode.chohort2.carRental.carProviderCoupon.*;
import com.amigoscode.chohort2.carRental.carProviderCoupon.VM.CarProviderCouponAvailabilityVM;
import com.amigoscode.chohort2.carRental.carProviderCoupon.VM.CarProviderCouponVM;
import com.amigoscode.chohort2.carRental.registration.RegistrationService;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


public class CarProviderCouponControllerIT extends AbstractTestContainer {

    @Autowired
    private CarProviderCouponRepository carProviderCouponRepository;
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private RegistrationService registrationService;
    private CarProviderCouponVM carProviderCouponVM;
    private CarProviderCouponAvailabilityVM updatedItem;
    private CarProviderRegistrationVM carProviderRegistrationVM;
    private static final String LOGIN_URL = "api/v1/auth/login";

    private static final String CAR_PROVIDER_COUPON_URL = "api/v1/car-provider-coupons";

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
                .setUsername("thata23")
                .setFirstName("thays")
                .setLastName("vieira")
                .setEmail("thr4rr@gmail.com")
                .setNin("9876543213")
                .setPassword("234567890");

        CarProviderVM carProviderVM = new CarProviderVM()
                .setName("car provider coupon test")
                .setCrNumber("898989898");

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
        CarProviderCoupon carProviderCoupon = new CarProviderCoupon()
                .setId(1L)
                .setCarProviderId(1L)
                .setCouponCode(carProviderCouponVM.getCouponCode())
                .setStartDate(carProviderCouponVM.getStartDate())
                .setEndDate(carProviderCouponVM.getEndDate())
                .setNumOfUsePerUser(carProviderCouponVM.getNumOfUsePerUser())
                .setIsAvailable(carProviderCouponVM.getIsAvailable());
        CarProviderCouponMapper.INSTANCE.toDto(carProviderCouponRepository.save(carProviderCoupon));

        webTestClient
                .patch()
                .uri(CAR_PROVIDER_COUPON_URL + "/" + "{couponId}", carProviderCoupon.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT)
                .body(Mono.just(updatedItem), CarProviderCouponAvailabilityVM.class)
                .exchange()
                .expectStatus().isAccepted()
                .expectBody(new ParameterizedTypeReference<CarProviderCouponDTO>() {
                })
                .returnResult()
                .getResponseBody();
        Optional<CarProviderCoupon>optionalCarProviderCoupon=carProviderCouponRepository.findById(carProviderCoupon.getCarProviderId());

        assertThat(optionalCarProviderCoupon.get().getIsAvailable()).isEqualTo(updatedItem.getIsAvailable());
    }
}
