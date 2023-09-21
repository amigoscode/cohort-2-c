package com.amigoscode.chohort2.carRental.carUser;

import com.amigoscode.chohort2.carRental.annotation.TransactionalService;
import com.amigoscode.chohort2.carRental.car.Car;
import lombok.RequiredArgsConstructor;

@TransactionalService
@RequiredArgsConstructor
public class CarUserService {

    private final CarUserRepository carUserRepository;


    public void save(CarUser carUser) {
        carUserRepository.save(carUser);
    }


    public CarUser createCarUserFromCar(Car car) {
        CarUser carUser = new CarUser()
                .setCarId(car.getId())
                .setRegistrationNumber(car.getRegistrationNumber())
                .setBrandCode(car.getBrandCode())
                .setBrandModelCode(car.getBrandModelCode())
                .setProductionYear(car.getProductionYear())
                .setMaxSpeed(car.getMaxSpeed())
                .setHorsePower(car.getHorsePower())
                .setRgbCode(car.getRgbCode())
                .setDescription(car.getDescription())
                .setCategoryCode(car.getCategoryCode())
                .setBookingStatusCode(car.getBookingStatusCode())
                .setPrice(car.getPrice())
                .setImgUrl(car.getImgUrl());
        return carUserRepository.save(carUser);
    }
}
