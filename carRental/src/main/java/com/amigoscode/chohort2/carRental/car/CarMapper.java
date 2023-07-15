package com.amigoscode.chohort2.carRental.car;

import com.amigoscode.chohort2.carRental.abstracts.EntityMapper;
import com.amigoscode.chohort2.carRental.brand.BrandMapper;
import com.amigoscode.chohort2.carRental.brandModel.BrandModelMapper;
import com.amigoscode.chohort2.carRental.lookupCode.LookupCodeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {LookupCodeMapper.class, BrandMapper.class, BrandModelMapper.class})
public interface CarMapper extends EntityMapper <Car, CarDTO> {
    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);
}
