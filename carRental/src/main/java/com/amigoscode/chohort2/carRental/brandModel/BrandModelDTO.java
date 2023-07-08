package com.amigoscode.chohort2.carRental.brandModel;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class BrandModelDTO {

    private Long id;

    private Integer brandId;

    private Integer code;


}
