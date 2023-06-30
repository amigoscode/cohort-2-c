package com.amigoscode.chohort2.carRental.registration;

import com.amigoscode.chohort2.carRental.AbstractTestContainer;
import com.amigoscode.chohort2.carRental.authority.Authority;
import com.amigoscode.chohort2.carRental.authority.AuthorityConstants;
import com.amigoscode.chohort2.carRental.authority.AuthorityService;
import com.amigoscode.chohort2.carRental.driverLicense.DriverLicenseService;
import com.amigoscode.chohort2.carRental.driverLicense.VM.DriverLicenseVM;
import com.amigoscode.chohort2.carRental.registration.VM.ClientRegistrationVM;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest extends AbstractTestContainer {

    @Mock
    private UserService userService;

    @Mock
    private AuthorityService authorityService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private DriverLicenseService driverLicenseService;



    @InjectMocks
    private RegistrationService underTest;

    @Test
    void givenClientRegistration_whenSave_thenCreateNewUser() {


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

        given(authorityService.findByName(AuthorityConstants.CLIENT))
                .willReturn(new Authority().setName(AuthorityConstants.CLIENT));

        given(passwordEncoder.encode(clientRegistrationVM.getPassword()))
                .willReturn("$2a$10$fi6PMDJmJaxFyoursx7Pw.LdMbpcbvAf0i7g9wO6fG8HrIJ6Kn0jm");


        // when - action or the behaviour that we are going test
        underTest.clientRegistration(clientRegistrationVM);

        // then verify the output

    }
}