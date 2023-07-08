package com.amigoscode.chohort2.carRental.carProvider;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class CarProviderDTO {

    private Long id;

    private String name;

    private String crNumber;

}
