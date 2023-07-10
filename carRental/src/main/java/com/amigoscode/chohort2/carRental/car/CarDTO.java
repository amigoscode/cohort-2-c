package com.amigoscode.chohort2.carRental.car;


import com.amigoscode.chohort2.carRental.lookupCode.LookupCodeDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
public class CarDTO {

    private Long id;

    private Long carProviderId;


    private UUID registrationNumber;

    private Integer brandCode;


    private Integer brandModelCode;


    private LocalDate productionYear;

    private Integer maxSpeed;

    private Integer horsePower;

    private String rgbCode;

    private String description;

    private Integer categoryCode;

    private LookupCodeDTO category;

    private Integer bookingStatusCode;

    private LookupCodeDTO bookingStatus;

    private Float price;

    private String imgUrl;

    private Boolean isVisible;


}
