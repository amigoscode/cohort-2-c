package com.amigoscode.chohort2.carRental.country;

import com.amigoscode.chohort2.carRental.abstracts.EntityMapper;
import com.amigoscode.chohort2.carRental.city.CityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {CityMapper.class})
public interface CountryMapper extends EntityMapper<Country, CountryDTO> {

    CountryMapper INSTANCE = Mappers.getMapper(CountryMapper.class);
}
