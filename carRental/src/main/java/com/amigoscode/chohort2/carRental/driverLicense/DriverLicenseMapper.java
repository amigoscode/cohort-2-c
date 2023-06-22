package com.amigoscode.chohort2.carRental.driverLicense;

import com.amigoscode.chohort2.carRental.abstracts.EntityMapper;
import com.amigoscode.chohort2.carRental.lookupCode.LookupCodeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses ={LookupCodeMapper.class})
public interface DriverLicenseMapper extends EntityMapper<DriverLicense,DriverLicenseDTO> {
DriverLicenseMapper INSTANT = Mappers.getMapper(DriverLicenseMapper.class);
}
