package com.amigoscode.chohort2.carRental.brand;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class BrandDTO {

    private Long id;
    private Integer code;
    private String name;


}
