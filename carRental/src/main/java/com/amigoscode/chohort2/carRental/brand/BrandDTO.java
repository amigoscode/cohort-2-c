package com.amigoscode.chohort2.carRental.brand;


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
public class BrandDTO {

    private Long id;
    private Integer code;
    private String name;


}
