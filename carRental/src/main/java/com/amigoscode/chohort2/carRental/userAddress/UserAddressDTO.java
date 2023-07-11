package com.amigoscode.chohort2.carRental.userAddress;


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
public class UserAddressDTO {

    private Long id;

    private Long userId;

    private Long cityId;


    private String location;


    private String postalCode;

}
