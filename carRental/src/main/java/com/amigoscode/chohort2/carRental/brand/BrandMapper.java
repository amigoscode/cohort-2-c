package com.amigoscode.chohort2.carRental.brand;

import com.amigoscode.chohort2.carRental.abstracts.EntityMapper;
import org.mapstruct.factory.Mappers;


public interface BrandMapper extends EntityMapper<Brand, BrandDTO> {

    BrandMapper INSTANCE = Mappers.getMapper(BrandMapper.class);
}
