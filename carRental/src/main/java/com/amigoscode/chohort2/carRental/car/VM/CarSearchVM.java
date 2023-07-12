package com.amigoscode.chohort2.carRental.car.VM;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
@Accessors(chain = true)
public class CarSearchVM implements Serializable {


    private Long providerId;

    private Integer brandCode;
    private Integer brandModelCode;

    private LocalDate productionYear;

    private Integer categoryCode;



}
