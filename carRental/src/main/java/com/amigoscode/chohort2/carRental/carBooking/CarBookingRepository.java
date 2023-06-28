package com.amigoscode.chohort2.carRental.carBooking;

import com.amigoscode.chohort2.carRental.abstracts.repository.CRJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarBookingRepository extends CRJpaRepository<CarBooking,Long> {
}
