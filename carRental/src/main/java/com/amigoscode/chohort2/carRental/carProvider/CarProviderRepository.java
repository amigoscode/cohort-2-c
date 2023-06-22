package com.amigoscode.chohort2.carRental.carProvider;

import com.amigoscode.chohort2.carRental.abstracts.repository.CRJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarProviderRepository extends CRJpaRepository <CarProvider,Long> {
}
