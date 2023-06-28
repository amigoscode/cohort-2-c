package com.amigoscode.chohort2.carRental.carUser;

import com.amigoscode.chohort2.carRental.abstracts.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarUserMapper extends EntityMapper<CarUser, CarUserDTO> {
    CarUserMapper INSTANCE = Mappers.getMapper(CarUserMapper.class);

}
