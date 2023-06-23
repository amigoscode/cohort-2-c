package com.amigoscode.chohort2.carRental.brandModel;

import com.amigoscode.chohort2.carRental.abstracts.EntityMapper;
import com.amigoscode.chohort2.carRental.brand.Brand;
import com.amigoscode.chohort2.carRental.brand.BrandDTO;
import com.amigoscode.chohort2.carRental.brand.BrandMapper;
import org.mapstruct.factory.Mappers;

public interface BrandModelMapper extends EntityMapper<Brand, BrandDTO> {
    BrandModelMapper INSTANCE = Mappers.getMapper(BrandModelMapper.class);
}
