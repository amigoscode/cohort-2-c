package com.amigoscode.chohort2.carRental.driverLicense;

import com.amigoscode.chohort2.carRental.abstracts.repository.CRJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverLicenseRepository extends CRJpaRepository<DriverLicense,Long> {

    Optional<DriverLicense> findByDriverLicenseNumber(String number);
}
