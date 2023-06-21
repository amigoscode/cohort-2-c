package com.amigoscode.chohort2.carRental.lookupCode;

import com.amigoscode.chohort2.carRental.abstracts.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LookupCodeMapper  extends EntityMapper<LookupCode,LookupCodeDTO> {
    LookupCodeMapper INSTANT = Mappers.getMapper(LookupCodeMapper.class);

}
