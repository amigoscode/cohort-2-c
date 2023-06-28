package com.amigoscode.chohort2.carRental.carBooking;

import com.amigoscode.chohort2.carRental.abstracts.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarBookingMapper extends EntityMapper <CarBooking,CarBookingDTO> {
    CarBookingMapper INSTANCE = Mappers.getMapper(CarBookingMapper.class);

}
