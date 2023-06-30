package com.amigoscode.chohort2.carRental.registration;

import com.amigoscode.chohort2.carRental.annotation.TransactionalService;
import com.amigoscode.chohort2.carRental.authority.Authority;
import com.amigoscode.chohort2.carRental.authority.AuthorityConstants;
import com.amigoscode.chohort2.carRental.authority.AuthorityService;
import com.amigoscode.chohort2.carRental.carProvider.CarProvider;
import com.amigoscode.chohort2.carRental.carProvider.CarProviderService;
import com.amigoscode.chohort2.carRental.carProviderUser.CarProviderUser;
import com.amigoscode.chohort2.carRental.carProviderUser.CarProviderUserService;
import com.amigoscode.chohort2.carRental.constants.ErrorConstants;
import com.amigoscode.chohort2.carRental.driverLicense.DriverLicense;
import com.amigoscode.chohort2.carRental.driverLicense.DriverLicenseService;
import com.amigoscode.chohort2.carRental.driverLicense.VM.DriverLicenseVM;
import com.amigoscode.chohort2.carRental.lookupCode.LookupCodes;
import com.amigoscode.chohort2.carRental.registration.VM.CarProviderRegistrationVM;
import com.amigoscode.chohort2.carRental.registration.VM.ClientRegistrationVM;
import com.amigoscode.chohort2.carRental.registration.VM.UserRegistrationVM;
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

    private final CarProviderUserService carProviderUserService;

    private final PasswordEncoder passwordEncoder;


    public void clientRegistration(ClientRegistrationVM clientRegistrationVM) {
        User user = createUser(clientRegistrationVM, AuthorityConstants.CLIENT, LookupCodes.UserType.client);

        DriverLicense driverLicense = DriverLicenseVM.vmToEntity(clientRegistrationVM.getDriverLicense());


        Validator.invalidateIfTure(() -> driverLicense.getExpiredDate().isBefore(LocalDate.now()),
                ErrorConstants.NOT_VALID_LICENSE,
                "the driver license is not valid");


        driverLicense.setUserId(user.getId());

        driverLicenseService.save(driverLicense);


    }

    public void carProviderRegistration(CarProviderRegistrationVM carProviderRegistrationVM) {
        User user = createUser(carProviderRegistrationVM, AuthorityConstants.CAR_PROVIDER, LookupCodes.UserType.carProvider);

        CarProvider carProvider = new CarProvider()
                .setName(carProviderRegistrationVM.getCarProviderVM().getName())
                .setCrNumber(carProviderRegistrationVM.getCarProviderVM().getCrNumber());
        carProviderService.saveCarProvider(carProvider);

        CarProviderUser carProviderUser = new CarProviderUser()
                .setUserId(user.getId())
                .setCarProviderId(carProvider.getId())
                .setCompanyAdmin(true);
        carProviderUserService.saveCarProviderUser(carProviderUser);

    }

    private User createUser(UserRegistrationVM userRegistrationVM, String authorityName, Integer typeCode) {
        Authority authority = authorityService.findByName(authorityName);

        User user = UserRegistrationVM.vmToEntity(userRegistrationVM);
        user
                .setPassword(passwordEncoder.encode(userRegistrationVM.getPassword()))
                .setTypeCode(typeCode)
                .setStatusCode(LookupCodes.UserStatus.active)
                .addAuthority(authority);

         userService.save(user);
        return user;
    }


}
