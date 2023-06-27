package com.amigoscode.chohort2.carRental.city;

import com.amigoscode.chohort2.carRental.abstracts.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CityMapper extends EntityMapper<City,CityDTO> {

    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

}
