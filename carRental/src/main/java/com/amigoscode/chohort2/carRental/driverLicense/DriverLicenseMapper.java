package com.amigoscode.chohort2.carRental.driverLicense;

import com.amigoscode.chohort2.carRental.abstracts.EntityMapper;
import com.amigoscode.chohort2.carRental.lookupCode.LookupCodeMapper;
import com.amigoscode.chohort2.carRental.user.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses ={UserMapper.class})
public interface DriverLicenseMapper extends EntityMapper<DriverLicense,DriverLicenseDTO> {
DriverLicenseMapper INSTANCE = Mappers.getMapper(DriverLicenseMapper.class);
}
