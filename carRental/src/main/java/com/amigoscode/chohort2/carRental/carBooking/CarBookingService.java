package com.amigoscode.chohort2.carRental.carBooking;

import com.amigoscode.chohort2.carRental.annotation.TransactionalService;
import com.amigoscode.chohort2.carRental.car.Car;
import com.amigoscode.chohort2.carRental.car.CarService;
import com.amigoscode.chohort2.carRental.carBooking.vm.UserBookingVM;
import com.amigoscode.chohort2.carRental.carUser.CarUser;
import com.amigoscode.chohort2.carRental.carUser.CarUserService;
import com.amigoscode.chohort2.carRental.constants.ErrorConstants;
import com.amigoscode.chohort2.carRental.lookupCode.LookupCodes;
import com.amigoscode.chohort2.carRental.user.User;
import com.amigoscode.chohort2.carRental.user.UserService;
import com.amigoscode.chohort2.carRental.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@TransactionalService
@RequiredArgsConstructor
public class CarBookingService {

    private final CarBookingRepository carBookingRepository;
    private final UserService userService;

    private CarUserService carUserService;

    private CarService carService;

    public CarBookingDTO createUserBooking(UserBookingVM userBookingVM) {
        User loggedInUser = userService.getLoggedInUser();

        Car car = carService.findCarById(userBookingVM.getCarId());

        Validator.invalidateIfTrue(() -> !car.getBookingStatusCode().equals(LookupCodes.CarBookingStatus.available),
                ErrorConstants.CAR_NOT_AVAILABLE);

        //TODO: make sure the coupon Code is valid

        CarUser carUser = carUserService.createCarUserFromCar(car);

        carService.updateCarBookingStatus(car,LookupCodes.CarBookingStatus.inUse);

        long daysOfBooking = ChronoUnit.DAYS.between(LocalDate.now(), userBookingVM.getCheckOut());
        float totalPrice = daysOfBooking * car.getPrice();

        CarBooking carBooking = new CarBooking()
                .setUserId(loggedInUser.getId())
                .setCarUserId(carUser.getCarId())
                .setCheckInDate(LocalDate.now())
                .setCheckOutDate(userBookingVM.getCheckOut())
                .setStatusCode(LookupCodes.UserBookingStatus.inUse)
                .setFinalPrice(totalPrice);


        return CarBookingMapper.INSTANCE.toDto(carBookingRepository.saveAndRefresh(carBooking));
    }


}
