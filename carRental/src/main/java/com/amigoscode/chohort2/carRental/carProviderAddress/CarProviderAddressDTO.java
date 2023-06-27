package com.amigoscode.chohort2.carRental.carProviderAddress;

import com.amigoscode.chohort2.carRental.city.CityDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarProviderAddressDTO implements Serializable {
    private Long id;
    private Long carProviderId;
    private CarProviderAddressDTO carProviderAddress;
    private Long cityId;
    private CityDTO cityDTO;
    private String location;
    private String postalCode;
}
