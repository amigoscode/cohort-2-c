package com.amigoscode.chohort2.carRental.carProvider;

import com.amigoscode.chohort2.carRental.abstracts.EntityMapper;
import com.amigoscode.chohort2.carRental.car.CarMapper;
import com.amigoscode.chohort2.carRental.lookupCode.LookupCodeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {LookupCodeMapper.class})
public interface CarProviderMapper extends EntityMapper<CarProvider,CarProviderDTO> {
    CarProviderMapper INSTANCE = Mappers.getMapper(CarProviderMapper.class);
}
