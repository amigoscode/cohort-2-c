package com.amigoscode.chohort2.carRental.carProviderCoupon;

import com.amigoscode.chohort2.carRental.authority.AuthorityConstants;
import com.amigoscode.chohort2.carRental.carProviderCoupon.VM.CarProviderCouponAvailabilityVM;
import com.amigoscode.chohort2.carRental.carProviderCoupon.VM.CarProviderCouponVM;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping (value = "api/v1/car-provider-coupons")
@RequiredArgsConstructor
public class CarProviderCouponController {
    private final Logger log = LoggerFactory.getLogger(CarProviderCouponController.class);
    private final CarProviderCouponService carProviderCouponService;

    @PostMapping
    @Secured({AuthorityConstants.CAR_PROVIDER})
    public ResponseEntity<CarProviderCouponDTO> createCarProviderCoupon(@RequestBody CarProviderCouponVM carProviderCouponVM){
        log.info("car provider coupon info {}", carProviderCouponVM);
        CarProviderCouponDTO couponDTO = carProviderCouponService.save(carProviderCouponVM);
        return ResponseEntity.status(HttpStatus.CREATED).body(couponDTO);
    }

    @PutMapping("{couponId}")
    @Secured({AuthorityConstants.CAR_PROVIDER})
    public ResponseEntity<CarProviderCouponDTO> updateCarProviderCouponAvailability(@PathVariable("couponId") Long couponId,@RequestBody CarProviderCouponAvailabilityVM carProviderCouponAvailabilityVM){
        log.info("car provider coupon info {}", carProviderCouponAvailabilityVM);
        CarProviderCouponDTO availabilityCouponDTO = carProviderCouponService.updateCouponAvailability(couponId,carProviderCouponAvailabilityVM);
        return ResponseEntity.ok().body(availabilityCouponDTO);
    }


}
