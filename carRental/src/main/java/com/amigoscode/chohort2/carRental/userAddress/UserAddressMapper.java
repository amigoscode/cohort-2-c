package com.amigoscode.chohort2.carRental.userAddress;

import com.amigoscode.chohort2.carRental.abstracts.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserAddressMapper extends EntityMapper<UserAddress,UserAddressDTO> {

    UserAddressMapper INSTANCE = Mappers.getMapper(UserAddressMapper.class);

}
