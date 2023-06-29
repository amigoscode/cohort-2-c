package com.amigoscode.chohort2.carRental.driverLicense;

import com.amigoscode.chohort2.carRental.annotation.TransactionalService;
import lombok.RequiredArgsConstructor;

@TransactionalService
@RequiredArgsConstructor
public class DriverLicenseService {
    private final DriverLicenseRepository driverLicenseRepository;

    public void save(DriverLicense driverLicense) {
        driverLicenseRepository.save(driverLicense);
    }
}
