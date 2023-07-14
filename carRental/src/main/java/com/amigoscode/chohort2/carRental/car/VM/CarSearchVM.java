package com.amigoscode.chohort2.carRental.car.VM;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Accessors(chain = true)
@ToString
@EqualsAndHashCode
public class CarSearchVM implements Serializable {

    private Integer sortingTypeCode;

    private Integer orderByTypeCode;

    private List<Long> carProviderIds;

    private UUID registrationNumber;

    private Integer brandCode;
    private Integer brandModelCode;

    private LocalDate productionYearFrom;
    private LocalDate productionYearTo;

    private Integer maxSpeedFrom;
    private Integer maxSpeedTo;

    private Integer horsePowerFrom;
    private Integer horsePowerTo;

    private String rgbCode;

    private String description;

    private Integer categoryCode;
    private Integer bookingStatusCode;

    private Float priceFrom;
    private Float priceTo;


}
