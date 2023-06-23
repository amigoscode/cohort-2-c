package com.amigoscode.chohort2.carRental.driverLicense;

import com.amigoscode.chohort2.carRental.abstracts.repository.CRJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverLicenseRepository extends CRJpaRepository<DriverLicense,Long> {
}
