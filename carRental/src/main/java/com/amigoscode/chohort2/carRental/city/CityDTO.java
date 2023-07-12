package com.amigoscode.chohort2.carRental.city;

import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CityDTO {

    private Long id;

    private Long countryId;

    private String name;


}
