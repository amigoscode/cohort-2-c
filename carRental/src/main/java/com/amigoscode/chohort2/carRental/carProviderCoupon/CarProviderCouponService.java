package com.amigoscode.chohort2.carRental.carProviderCoupon;

import com.amigoscode.chohort2.carRental.annotation.TransactionalService;
import com.amigoscode.chohort2.carRental.carProviderCoupon.VM.CarProviderCouponAvailabilityVM;
import com.amigoscode.chohort2.carRental.carProviderCoupon.VM.CarProviderCouponVM;
import com.amigoscode.chohort2.carRental.carProviderUser.CarProviderUser;
import com.amigoscode.chohort2.carRental.carProviderUser.CarProviderUserService;
import com.amigoscode.chohort2.carRental.exception.ApiRequestException;
import com.amigoscode.chohort2.carRental.user.User;
import com.amigoscode.chohort2.carRental.user.UserService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;


@TransactionalService
@RequiredArgsConstructor
public class CarProviderCouponService {
    private final CarProviderCouponRepository carProviderCouponRepository;
    private final CarProviderUserService carProviderUserService;
    private final UserService userService;

    public CarProviderCouponDTO save(CarProviderCouponVM carProviderCouponVM) {
        CarProviderCoupon carProviderCoupon = new CarProviderCoupon()
                .setCarProviderId(getCarProvideId())
                .setCouponCode(carProviderCouponVM.getCouponCode())
                .setStartDate(carProviderCouponVM.getStartDate())
                .setEndDate(carProviderCouponVM.getEndDate())
                .setNumOfUsePerUser(carProviderCouponVM.getNumOfUsePerUser())
                .setIsAvailable(carProviderCouponVM.getIsAvailable());
        return CarProviderCouponMapper.INSTANCE.toDto(carProviderCouponRepository.save(carProviderCoupon));
    }

    public CarProviderCouponDTO updateCouponAvailability(Long id,CarProviderCouponAvailabilityVM carProviderCouponAvailabilityVM){
          CarProviderCoupon carProviderCoupon = getCarProviderCouponById(id);
          if(carProviderCoupon.getCarProviderId().equals(getCarProvideId())) {
              carProviderCoupon.setIsAvailable(carProviderCouponAvailabilityVM.getIsAvailable());
          }
        return CarProviderCouponMapper.INSTANCE.toDto(carProviderCouponRepository.save(carProviderCoupon));
    }

    private CarProviderCoupon getCarProviderCouponById(Long id){
        CarProviderCoupon carProviderCoupon = carProviderCouponRepository.findById(id)
                .orElseThrow(()->new ApiRequestException("Coupon not found"));
        return carProviderCoupon;
    }

    private Long getCarProvideId() {
        User user = userService.getLoggedInUser();
        CarProviderUser carProviderUser = carProviderUserService.findCarProviderUserByUserId(user.getId());
        return carProviderUser.getCarProviderId();
    }
}
