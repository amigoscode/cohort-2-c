package com.amigoscode.chohort2.carRental.carProviderCoupon;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping (value = "api/v1/car-provider-coupons")
@RequiredArgsConstructor
public class CarProviderCouponController {
    private final CarProviderCouponService carProviderCouponService;
}
