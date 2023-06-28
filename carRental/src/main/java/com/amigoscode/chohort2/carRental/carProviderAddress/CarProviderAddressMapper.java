package com.amigoscode.chohort2.carRental.carProviderAddress;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface CarProviderAddressMapper {
    CarProviderAddressMapper INSTANCE = Mappers.getMapper(CarProviderAddressMapper.class);
}
