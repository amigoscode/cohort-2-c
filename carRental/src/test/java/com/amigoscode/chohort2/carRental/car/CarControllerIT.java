package com.amigoscode.chohort2.carRental.car;


import com.amigoscode.chohort2.carRental.AbstractTestContainer;
import com.amigoscode.chohort2.carRental.BaseIT;
import com.amigoscode.chohort2.carRental.car.VM.CarSearchVM;
import com.amigoscode.chohort2.carRental.car.VM.CarVM;
import com.amigoscode.chohort2.carRental.carProvider.VM.CarProviderVM;
import com.amigoscode.chohort2.carRental.carProviderCoupon.VM.CarProviderCouponAvailabilityVM;
import com.amigoscode.chohort2.carRental.carProviderUser.CarProviderUser;
import com.amigoscode.chohort2.carRental.registration.VM.CarProviderRegistrationVM;
import com.amigoscode.chohort2.carRental.registration.VM.ClientRegistrationVM;
import com.amigoscode.chohort2.carRental.user.UserRepository;
import com.amigoscode.chohort2.carRental.user.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@ActiveProfiles("test")
public class CarControllerIT extends BaseIT {

    List<CarProviderUser> carProviderUsers;

    @Autowired
    CarService carService;

    @Autowired
    UserRepository userRepository;

    private static final String API_URL= "cars";

    private CarSearchVM carSearchVM;


        @BeforeAll
        static void beforeAll(){


    }
    @BeforeEach
    void setUpProviders() {
        carProviderUsers = setUpCarProviders();
    }

    @AfterEach
    void cleanDb(){

    }
    @Test
    public void contextLoads() throws IOException, InterruptedException {
        System.out.println(carService.getS3Bucket().getS3domains().toString());
        System.out.println(localstack.execInContainer("awslocal", "s3api", "list-buckets"));
    }

    @Test
    public void whenCarProviderAddsACar_thenItSucceeds (){

            CarVM vm = new CarVM()
                    .setBrandCode(1000)
                    .setBrandModelCode(1001)
                    .setCategoryCode(1)
                    .setRgbCode("f1f1f1")
                    .setDescription("description")
                    .setHorsePower(600)
                    .setPrice(500f)
                    .setMaxSpeed(340)
                    .setProductionYear(LocalDate.ofEpochDay(2021-1-1))
                    .setImgUrl("fsfsdf");

            String jwtToken = getJwtTokenForUser(
                    userRepository.findByIdWithAuthorites(
                            carProviderUsers.get(0).getUserId()
                    ).orElseThrow(()-> new RuntimeException("no user found")));
            webTestClient
                    .post()
                    .uri(BASE_URL+API_URL)
                    .headers(setHeaders(jwtToken))
                    .body(Mono.just(vm), CarVM.class)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(String.class)
                    .returnResult()
                    .getResponseBody();

//        webTestClient.post()
//                .uri( uriBuilder -> uriBuilder.path("api/v1/car-provider-coupons/{couponId}")
//                        .build(2) )
//                .header(HttpHeaders.AUTHORIZATION, jwt)
//                .body(Mono.just(vm), CarProviderCouponAvailabilityVM.class).
//        ...

    }

    @Test
    public void whenSearchWithAllCriteriaBeingNull_thenShouldReturnAllCars() {
        carSearchVM = new CarSearchVM()
                .setCarProviderIds(null)
                .setRegistrationNumber(null)
                .setBrandCode(null)
                .setBrandModelCode(null)
                .setProductionYearFrom(null)
                .setProductionYearTo(null)
                .setMaxSpeedFrom(null)
                .setMaxSpeedTo(null)
                .setHorsePowerFrom(null)
                .setHorsePowerTo(null)
                .setRgbCode(null)
                .setDescription(null)
                .setCategoryCode(null)
                .setBookingStatusCode(null)
                .setPriceFrom(null)
                .setPriceTo(null);

        var response = webTestClient
                .post()
                .uri(BASE_URL+API_URL+"/search")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(carSearchVM), CarSearchVM.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody().returnResult();

        System.out.println(response);
    }
}
