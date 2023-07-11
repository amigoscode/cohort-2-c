package com.amigoscode.chohort2.carRental.carProvider;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@ToString
@EqualsAndHashCode
public class CarProviderDTO {

    private Long id;

    private String name;

    private String crNumber;

}
