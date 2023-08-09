package com.amigoscode.chohort2.carRental.testUtils;

import com.amigoscode.chohort2.carRental.driverLicense.DriverLicense;

import java.time.LocalDate;

public class TestDriverLicenseFactory {

    private static int testDriverLicenseSeq = 0;

    public static DriverLicense createTestDriverLicense(){
        return new DriverLicense()
                .setDriverLicenseNumber("diverLicenseNumber"+testDriverLicenseSeq)
                .setExpiredDate(LocalDate.now().plusYears(3))
                .setIssuedAt(LocalDate.now().minusYears(3))
                .setIssuedIn(1L);
    }
}
