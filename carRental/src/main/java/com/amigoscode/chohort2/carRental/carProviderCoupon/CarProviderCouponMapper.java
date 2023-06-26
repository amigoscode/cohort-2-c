package com.amigoscode.chohort2.carRental.carProviderCoupon;

import com.amigoscode.chohort2.carRental.abstracts.EntityMapper;
import com.amigoscode.chohort2.carRental.car.CarMapper;
import com.amigoscode.chohort2.carRental.carProvider.CarProvider;
import com.amigoscode.chohort2.carRental.carProvider.CarProviderDTO;
import org.mapstruct.factory.Mappers;

public interface CarProviderCouponMapper extends EntityMapper<CarProviderCoupon, CarProviderCouponDTO> {

    CarProviderCouponMapper INSTANCE = Mappers.getMapper(CarProviderCouponMapper.class);
}
