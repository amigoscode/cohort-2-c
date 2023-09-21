package com.amigoscode.chohort2.carRental.carBooking;

import com.amigoscode.chohort2.carRental.annotation.TransactionalService;
import com.amigoscode.chohort2.carRental.car.Car;
import com.amigoscode.chohort2.carRental.car.CarService;
import com.amigoscode.chohort2.carRental.carBooking.vm.UserBookingVM;
import com.amigoscode.chohort2.carRental.carProviderCoupon.CarProviderCoupon;
import com.amigoscode.chohort2.carRental.carProviderCoupon.CarProviderCouponService;
import com.amigoscode.chohort2.carRental.carUser.CarUser;
import com.amigoscode.chohort2.carRental.carUser.CarUserService;
import com.amigoscode.chohort2.carRental.constants.ErrorConstants;
import com.amigoscode.chohort2.carRental.lookupCode.LookupCodes;
import com.amigoscode.chohort2.carRental.user.User;
import com.amigoscode.chohort2.carRental.user.UserService;
import com.amigoscode.chohort2.carRental.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@TransactionalService
@RequiredArgsConstructor
public class CarBookingService {

    private final CarBookingRepository carBookingRepository;
    private final UserService userService;

    private final CarUserService carUserService;

    private final CarService carService;

    private final CarProviderCouponService carProviderCouponService;

    public CarBookingDTO createUserBooking(UserBookingVM userBookingVM) {
        User loggedInUser = userService.getLoggedInUser();

        Car car = carService.findCarById(userBookingVM.getCarId());

        Validator.invalidateIfTrue(() -> !car.getBookingStatusCode().equals(LookupCodes.CarBookingStatus.available), ErrorConstants.CAR_NOT_AVAILABLE);


        CarUser carUser = carUserService.createCarUserFromCar(car);

        carService.updateCarBookingStatus(car, LookupCodes.CarBookingStatus.inUse);

        long daysOfBooking = ChronoUnit.DAYS.between(LocalDate.now(), userBookingVM.getCheckOut());
        float price = daysOfBooking * car.getPrice();
        float totalPrice = price - calculateCoupon(userBookingVM.getCouponCode(), price);

        CarBooking carBooking = new CarBooking().setUserId(loggedInUser.getId()).setCarUserId(carUser.getCarId()).setCheckInDate(LocalDate.now()).setCheckOutDate(userBookingVM.getCheckOut()).setStatusCode(LookupCodes.UserBookingStatus.inUse).setFinalPrice(totalPrice);


        return CarBookingMapper.INSTANCE.toDto(carBookingRepository.saveAndRefresh(carBooking));
    }


    private float calculateCoupon(String couponCode, float price) {
        if (StringUtils.isBlank(couponCode)) {
            return 0.0f;
        }


        CarProviderCoupon coupon = carProviderCouponService.findCouponByCode(couponCode);
        Validator.invalidateIfTrue(() -> coupon.getEndDate().isBefore(LocalDate.now()), "coupon is expired");

        Validator.invalidateIfTrue(() -> !coupon.getIsAvailable(), "coupon is not available for use");

        //TODO: add userCoupon entity

        return price * (coupon.getDiscountPercentage() / 100);

    }

}
