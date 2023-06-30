package com.amigoscode.chohort2.carRental.registration;

import com.amigoscode.chohort2.carRental.annotation.TransactionalService;
import com.amigoscode.chohort2.carRental.authority.Authority;
import com.amigoscode.chohort2.carRental.authority.AuthorityConstants;
import com.amigoscode.chohort2.carRental.authority.AuthorityService;
import com.amigoscode.chohort2.carRental.carProvider.CarProviderService;
import com.amigoscode.chohort2.carRental.constants.ErrorConstants;
import com.amigoscode.chohort2.carRental.driverLicense.DriverLicense;
import com.amigoscode.chohort2.carRental.driverLicense.DriverLicenseService;
import com.amigoscode.chohort2.carRental.driverLicense.VM.DriverLicenseVM;
import com.amigoscode.chohort2.carRental.lookupCode.LookupCodes;
import com.amigoscode.chohort2.carRental.registration.VM.CarProviderRegistrationVM;
import com.amigoscode.chohort2.carRental.registration.VM.ClientRegistrationVM;
import com.amigoscode.chohort2.carRental.user.User;
import com.amigoscode.chohort2.carRental.user.UserService;
import com.amigoscode.chohort2.carRental.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@TransactionalService
@RequiredArgsConstructor
public class RegistrationService {

    private final UserService userService;

    private final CarProviderService carProviderService;

    private final AuthorityService authorityService;

    private final DriverLicenseService driverLicenseService;

    private final PasswordEncoder passwordEncoder;


    public void clientRegistration(ClientRegistrationVM clientRegistrationVM) {
        Authority authority = authorityService.findByName(AuthorityConstants.CLIENT);

        User user = ClientRegistrationVM.vmToEntity(clientRegistrationVM);
        user
                .setPassword(passwordEncoder.encode(clientRegistrationVM.getPassword()))
                .setTypeCode(LookupCodes.UserType.client)
                .setStatusCode(LookupCodes.UserStatus.active)
                .addAuthority(authority);

        userService.save(user);

        DriverLicense driverLicense = DriverLicenseVM.vmToEntity(clientRegistrationVM.getDriverLicense());


        Validator.invalidateIfTure(()-> driverLicense.getExpiredDate().isBefore(LocalDate.now()),
                ErrorConstants.NOT_VALID_LICENSE,
                "the driver license is not valid");


        driverLicense.setUserId(user.getId());

        driverLicenseService.save(driverLicense);


    }
    public void carProviderRegistration(CarProviderRegistrationVM carProviderRegistrationVM) {
        Authority authority = authorityService.findByName(AuthorityConstants.CAR_PROVIDER);

        User user = CarProviderRegistrationVM.vmToEntity(carProviderRegistrationVM);
        user
                .setPassword(passwordEncoder.encode(carProviderRegistrationVM.getPassword()))
                .setTypeCode(LookupCodes.UserType.carProvider)
                .setStatusCode(LookupCodes.UserStatus.active)
                .addAuthority(authority);

        userService.save(user);
    }


}
