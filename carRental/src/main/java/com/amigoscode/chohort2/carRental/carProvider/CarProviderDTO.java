package com.amigoscode.chohort2.carRental.carProvider;

import com.amigoscode.chohort2.carRental.car.Car;
import com.amigoscode.chohort2.carRental.carProviderCoupon.CarProviderCouponDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Accessors(chain = true)
public class CarProviderDTO {

    private Long id;

    private String name;

    private String crNumber;

    private List<Car> cars = new ArrayList<>();

    private List <CarProviderCouponDTO> carProviderCoupons = new ArrayList<>();
}
