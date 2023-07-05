package com.amigoscode.chohort2.carRental.carProviderCoupon;

import com.amigoscode.chohort2.carRental.annotation.TransactionalService;
import com.amigoscode.chohort2.carRental.carProviderCoupon.VM.CarProviderCouponVM;
import com.amigoscode.chohort2.carRental.carProviderUser.CarProviderUserRepository;
import com.amigoscode.chohort2.carRental.constants.ErrorConstants;
import com.amigoscode.chohort2.carRental.user.User;
import com.amigoscode.chohort2.carRental.user.UserService;
import lombok.RequiredArgsConstructor;

@TransactionalService
@RequiredArgsConstructor
public class CarProviderCouponService {
    private final CarProviderCouponRepository carProviderCouponRepository;
    private final CarProviderUserRepository carProviderUserRepository;
    private final UserService userService;

    public void save(CarProviderCouponVM carProviderCouponVM){
        CarProviderCoupon carProviderCoupon = new CarProviderCoupon()
                .setCarProviderId(getCarProvideId())
                .setCouponCode(carProviderCouponVM.getCouponCode())
                .setStartDate(carProviderCouponVM.getStartDate())
                .setEndDate(carProviderCouponVM.getEndDate())
                .setNumOfUsePerUser(carProviderCouponVM.getNumOfUsePerUser())
                .setIsAvailable(carProviderCouponVM.getIsAvailable());
        carProviderCouponRepository.save(carProviderCoupon);
    }
    private Long getCarProvideId() {
        User user = userService.getLoggedInUser();
        var carProviderUserOptional = carProviderUserRepository.findByUserId(user.getId()).orElseThrow(()-> new NullPointerException(ErrorConstants.INTERNAL));
        return carProviderUserOptional.getCarProviderId();
    }
    }
