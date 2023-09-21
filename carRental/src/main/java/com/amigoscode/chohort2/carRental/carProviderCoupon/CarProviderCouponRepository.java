package com.amigoscode.chohort2.carRental.carProviderCoupon;

import com.amigoscode.chohort2.carRental.abstracts.repository.CRJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarProviderCouponRepository extends CRJpaRepository<CarProviderCoupon, Long> {
    @Query("select u from CarProviderCoupon  u where u.couponCode=:couponCode")
    Optional<CarProviderCoupon> findByCouponCode(@Param("couponCode") String couponCode);
}
