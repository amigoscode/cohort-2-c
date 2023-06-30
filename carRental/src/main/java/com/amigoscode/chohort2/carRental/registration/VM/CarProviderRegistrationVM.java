package com.amigoscode.chohort2.carRental.registration.VM;


import com.amigoscode.chohort2.carRental.driverLicense.VM.DriverLicenseVM;
import com.amigoscode.chohort2.carRental.user.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarProviderRegistrationVM implements Serializable {


    @NotEmpty
    private String username;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String email;

    @NotEmpty
    private String nin;

    @NotEmpty
    @ToString.Exclude
    private String password;


    public static User vmToEntity(CarProviderRegistrationVM carProviderRegistrationVM){
        return new User()
                .setUsername(carProviderRegistrationVM.username)
                .setFirstName(carProviderRegistrationVM.firstName)
                .setLastName(carProviderRegistrationVM.lastName)
                .setEmail(carProviderRegistrationVM.email)
                .setNin(carProviderRegistrationVM.nin);
    }
}
