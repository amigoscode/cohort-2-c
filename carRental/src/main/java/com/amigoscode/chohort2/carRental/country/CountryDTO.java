package com.amigoscode.chohort2.carRental.country;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CountryDTO {


    private Long id;


    private String ccCode;


    private String callingCode;


}
