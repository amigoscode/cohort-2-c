package com.amigoscode.chohort2.carRental.carProviderUser;

import com.amigoscode.chohort2.carRental.car.CarMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarProviderUserMapper {
    CarProviderUserMapper INSTANCE = Mappers.getMapper(CarProviderUserMapper.class);
}
