package com.amigoscode.chohort2.carRental.driverLicense.VM;



import com.amigoscode.chohort2.carRental.driverLicense.DriverLicense;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class DriverLicenseVM implements Serializable {

    @NotEmpty
    private String driverLicenseNumber;

    @NotEmpty
    private Long issuedIn;

    @NotNull
    private LocalDate issuedAt;

    @NotNull
    private LocalDate expiredDate;


    public static DriverLicense vmToEntity(DriverLicenseVM driverLicenseVM){

    return new DriverLicense()
            .setDriverLicenseNumber(driverLicenseVM.driverLicenseNumber)
            .setIssuedIn(driverLicenseVM.issuedIn)
            .setIssuedAt(driverLicenseVM.issuedAt)
            .setExpiredDate(driverLicenseVM.expiredDate);
    }





}
