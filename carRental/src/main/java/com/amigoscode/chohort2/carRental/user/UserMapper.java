package com.amigoscode.chohort2.carRental.user;

import com.amigoscode.chohort2.carRental.abstracts.EntityMapper;
import com.amigoscode.chohort2.carRental.lookupCode.LookupCodeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {LookupCodeMapper.class})
public interface UserMapper extends EntityMapper<User, UserDTO> {
    UserMapper INSTANT = Mappers.getMapper(UserMapper.class);

}
