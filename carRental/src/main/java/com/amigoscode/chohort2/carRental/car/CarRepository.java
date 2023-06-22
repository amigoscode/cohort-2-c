package com.amigoscode.chohort2.carRental.car;

import com.amigoscode.chohort2.carRental.abstracts.repository.CRJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends CRJpaRepository <Car,Long> {
}
