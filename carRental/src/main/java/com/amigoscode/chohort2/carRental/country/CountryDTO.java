package com.amigoscode.chohort2.carRental.country;

import com.amigoscode.chohort2.carRental.city.CityDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CountryDTO {


    private Long id;


    private String ccCode;


    private String callingCode;

    private List<CityDTO> cities;


}
