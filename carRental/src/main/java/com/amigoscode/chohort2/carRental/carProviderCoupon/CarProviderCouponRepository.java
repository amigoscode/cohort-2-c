package com.amigoscode.chohort2.carRental.carProviderCoupon;

import com.amigoscode.chohort2.carRental.abstracts.repository.CRJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarProviderCouponRepository extends CRJpaRepository <CarProviderCoupon, Long> {
}
