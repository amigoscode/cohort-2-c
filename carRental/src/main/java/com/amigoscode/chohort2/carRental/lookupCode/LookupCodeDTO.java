package com.amigoscode.chohort2.carRental.lookupCode;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class LookupCodeDTO {

    private String key;

    private Integer code;

    private String name;

}
