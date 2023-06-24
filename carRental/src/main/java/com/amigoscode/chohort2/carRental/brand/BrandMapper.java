package com.amigoscode.chohort2.carRental.brand;

import com.amigoscode.chohort2.carRental.abstracts.EntityMapper;
import com.amigoscode.chohort2.carRental.car.CarMapper;
import com.amigoscode.chohort2.carRental.lookupCode.LookupCodeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


public interface BrandMapper extends EntityMapper<Brand, BrandDTO> {

    BrandMapper INSTACE = Mappers.getMapper(BrandMapper.class);
}
