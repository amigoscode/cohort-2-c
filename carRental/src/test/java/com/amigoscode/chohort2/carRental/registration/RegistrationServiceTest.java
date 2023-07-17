package com.amigoscode.chohort2.carRental.registration;


import com.amigoscode.chohort2.carRental.authority.Authority;
import com.amigoscode.chohort2.carRental.authority.AuthorityConstants;
import com.amigoscode.chohort2.carRental.authority.AuthorityService;
import com.amigoscode.chohort2.carRental.carProvider.CarProvider;
import com.amigoscode.chohort2.carRental.carProvider.CarProviderService;
import com.amigoscode.chohort2.carRental.carProvider.VM.CarProviderVM;
import com.amigoscode.chohort2.carRental.carProviderUser.CarProviderUser;
import com.amigoscode.chohort2.carRental.carProviderUser.CarProviderUserService;
import com.amigoscode.chohort2.carRental.driverLicense.DriverLicense;
import com.amigoscode.chohort2.carRental.driverLicense.DriverLicenseService;
import com.amigoscode.chohort2.carRental.driverLicense.VM.DriverLicenseVM;
import com.amigoscode.chohort2.carRental.registration.VM.CarProviderRegistrationVM;
import com.amigoscode.chohort2.carRental.registration.VM.ClientRegistrationVM;
import com.amigoscode.chohort2.carRental.user.User;
import com.amigoscode.chohort2.carRental.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


import java.time.LocalDate;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private AuthorityService authorityService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private DriverLicenseService driverLicenseService;

    @Mock
    private  CarProviderUserService carProviderUserService;

    @Mock
    private  CarProviderService carProviderService;


    @InjectMocks
    private RegistrationService underTest;

    @Test
    void givenClientRegistration_whenSave_thenCreateNewUser() {


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

        given(authorityService.findByName(AuthorityConstants.CLIENT))
                .willReturn(new Authority().setName(AuthorityConstants.CLIENT));

        given(passwordEncoder.encode(clientRegistrationVM.getPassword()))
                .willReturn("encoded");


        // when - action or the behaviour that we are going test
        underTest.registration(clientRegistrationVM);

        // then verify the output
        verify(userService).save(any(User.class));
        verify(driverLicenseService).save(any(DriverLicense.class));

    }


    @Test
    void givenCarProviderRegistration_whenSave_thenCreateNewUser() {
        // given precondition or setup
        CarProviderRegistrationVM carProviderRegistrationVM = (CarProviderRegistrationVM) new CarProviderRegistrationVM()
                .setUsername("esmaeeil")
                .setFirstName("esmaeeil")
                .setLastName("enani")
                .setEmail("enani@gmail.com")
                .setNin("123456789")
                .setPassword("123456789");

        CarProviderVM carProviderVM = new CarProviderVM()
                .setCrNumber("1234567890")
                .setName("my company");
        carProviderRegistrationVM.setCarProviderVM(carProviderVM);

        given(authorityService.findByName(AuthorityConstants.CAR_PROVIDER))
                .willReturn(new Authority().setName(AuthorityConstants.CAR_PROVIDER));

        given(passwordEncoder.encode(carProviderRegistrationVM.getPassword()))
                .willReturn("encoded");
        // when - action or the behaviour that we are going test
        underTest.registration(carProviderRegistrationVM);


        // then verify the output
        verify(userService).save(any(User.class));
        verify(carProviderService).saveCarProvider(any(CarProvider.class));
        verify(carProviderUserService).saveCarProviderUser(any(CarProviderUser.class));

    }
}