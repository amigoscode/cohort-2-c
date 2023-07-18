package com.amigoscode.chohort2.carRental.carProviderCoupon;

import com.amigoscode.chohort2.carRental.abstracts.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface CarProviderCouponMapper extends EntityMapper<CarProviderCoupon, CarProviderCouponDTO> {

    CarProviderCouponMapper INSTANCE = Mappers.getMapper(CarProviderCouponMapper.class);
}
