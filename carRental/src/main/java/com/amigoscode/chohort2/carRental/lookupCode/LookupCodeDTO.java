package com.amigoscode.chohort2.carRental.lookupCode;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
public class LookupCodeDTO {

    private String key;

    private Integer code;

    private String name;

}
