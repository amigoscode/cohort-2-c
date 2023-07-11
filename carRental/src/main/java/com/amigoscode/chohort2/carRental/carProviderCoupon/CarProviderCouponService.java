package com.amigoscode.chohort2.carRental.carProviderCoupon;

import com.amigoscode.chohort2.carRental.annotation.TransactionalService;
import com.amigoscode.chohort2.carRental.carProviderCoupon.VM.CarProviderCouponVM;
import com.amigoscode.chohort2.carRental.carProviderUser.CarProviderUserRepository;
import com.amigoscode.chohort2.carRental.constants.ErrorConstants;
import com.amigoscode.chohort2.carRental.exception.ApiRequestException;
import com.amigoscode.chohort2.carRental.user.User;
import com.amigoscode.chohort2.carRental.user.UserService;
import lombok.RequiredArgsConstructor;


@TransactionalService
@RequiredArgsConstructor
public class CarProviderCouponService {
    private final CarProviderUserRepository carProviderUserRepository;
    private  final CarProviderCouponRepository carProviderCouponRepository;
    private final UserService userService;

    public void save(CarProviderCouponVM carProviderCouponVM){
        CarProviderCoupon carProviderCoupon=findCarProviderUser(carProviderCouponVM);
        carProviderCouponRepository.save(carProviderCoupon);
    }
    private CarProviderCoupon findCarProviderUser(CarProviderCouponVM carProviderCouponVM) {
        User user = userService.getLoggedInUser();
        var carProviderUserOptional = carProviderUserRepository.findByUserId(user.getId());
        if (carProviderUserOptional.get().getCarProviderId() == null)
            throw new ApiRequestException(ErrorConstants.USER_NOT_FOUND);
        return new CarProviderCoupon()
                .setCarProviderId(carProviderUserOptional.get().getCarProviderId())
                .setCouponCode(carProviderCouponVM.getCouponCode())
                .setStartDate(carProviderCouponVM.getStartDate())
                .setEndDate(carProviderCouponVM.getEndDate())
                .setNumOfUsePerUser(carProviderCouponVM.getNumOfUsePerUser())
                .setIsAvailable(carProviderCouponVM.getIsAvailable());
    }
    }
