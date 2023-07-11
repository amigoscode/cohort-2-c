package com.amigoscode.chohort2.carRental.country;

import com.amigoscode.chohort2.carRental.city.CityDTO;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@EqualsAndHashCode
public class CountryDTO {


    private Long id;


    private String ccCode;


    private String callingCode;

    private List<CityDTO> cities;


}
