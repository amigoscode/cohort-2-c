package com.amigoscode.chohort2.carRental.registration;

import com.amigoscode.chohort2.carRental.AbstractTestContainer;
import com.amigoscode.chohort2.carRental.AbstractTestContainerWithDB;
import com.amigoscode.chohort2.carRental.AbstractTestContainerWithS3;
import com.amigoscode.chohort2.carRental.AbstractTestContainerWithS3AndDB;
import com.amigoscode.chohort2.carRental.authority.AuthorityConstants;
import com.amigoscode.chohort2.carRental.carProvider.CarProviderRepository;
import com.amigoscode.chohort2.carRental.carProvider.VM.CarProviderVM;
import com.amigoscode.chohort2.carRental.carProviderUser.CarProviderUser;
import com.amigoscode.chohort2.carRental.carProviderUser.CarProviderUserRepository;
import com.amigoscode.chohort2.carRental.driverLicense.DriverLicenseRepository;
import com.amigoscode.chohort2.carRental.driverLicense.VM.DriverLicenseVM;
import com.amigoscode.chohort2.carRental.registration.VM.CarProviderRegistrationVM;
import com.amigoscode.chohort2.carRental.registration.VM.ClientRegistrationVM;
import com.amigoscode.chohort2.carRental.user.User;
import com.amigoscode.chohort2.carRental.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class RegistrationControllerIT extends AbstractTestContainerWithS3AndDB {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DriverLicenseRepository driverLicenseRepository;

    @Autowired
    private CarProviderRepository carProviderRepository;

    @Autowired
    private CarProviderUserRepository carProviderUserRepository;




    private static final String API_URL = "api/v1/registrations/";


    @Test
    void givenClientRegistration_whenSave_thenCreateNewClient() {
        // given precondition or setup
        ClientRegistrationVM clientRegistrationVM = (ClientRegistrationVM) new ClientRegistrationVM()
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
                .uri(API_URL + "clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(clientRegistrationVM), ClientRegistrationVM.class)
                .exchange()
                .expectStatus().isCreated();


        // then verify the output
        assertThat(userRepository.findByUsernameWithAuthorities(clientRegistrationVM.getUsername()).isPresent())
                .isTrue();



    }

    @Test
    void givenCarProviderRegistration_whenSave_thenCreateNewCarProvider() {
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


        // then verify the output
        Optional<User> userOpt = userRepository.findByUsernameWithAuthorities(carProviderRegistrationVM.getUsername());
        assertThat(userOpt).isPresent();
        User user = userOpt.get();
        assertThat(user.getAuthorities())
                .anyMatch(a -> a.getName().equals(AuthorityConstants.CAR_PROVIDER));

        assertThat(user.getAuthorities())
                .noneMatch(a -> a.getName().equals(AuthorityConstants.CLIENT))
                .noneMatch(a -> a.getName().equals(AuthorityConstants.ADMIN));

        Optional<CarProviderUser> carProviderUserOpt = carProviderUserRepository.findByUserId(user.getId());
        assertThat(carProviderUserOpt).isPresent();
        CarProviderUser carProviderUser = carProviderUserOpt.get();
        assertThat(carProviderRepository.findById(carProviderUser.getCarProviderId())).isPresent();


    }
}