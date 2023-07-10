package com.amigoscode.chohort2.carRental.brandModel;

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
public class BrandModelDTO {

    private Long id;

    private Integer brandId;

    private Integer code;


}
