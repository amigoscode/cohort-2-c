package com.amigoscode.chohort2.carRental.registration.VM;


import com.amigoscode.chohort2.carRental.driverLicense.VM.DriverLicenseVM;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;



@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class ClientRegistrationVM extends UserRegistrationVM {



    @NotNull
    private DriverLicenseVM driverLicense;




}
