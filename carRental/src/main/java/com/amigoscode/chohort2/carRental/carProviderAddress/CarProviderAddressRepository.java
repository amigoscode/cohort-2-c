package com.amigoscode.chohort2.carRental.carProviderAddress;

import com.amigoscode.chohort2.carRental.abstracts.repository.CRJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarProviderAddressRepository extends CRJpaRepository<CarProviderAddress,Long> {
}
