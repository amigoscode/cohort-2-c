package com.amigoscode.chohort2.carRental.carUser;

import com.amigoscode.chohort2.carRental.brand.Brand;
import com.amigoscode.chohort2.carRental.brandModel.BrandModel;
import com.amigoscode.chohort2.carRental.car.Car;
import com.amigoscode.chohort2.carRental.lookupCode.LookupCode;
import lombok.*;
import lombok.experimental.Accessors;


import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@EqualsAndHashCode
public class CarUserDTO {

    private Long id;

    private Long carId;

    private UUID registrationNumber;

    private Integer brandCode;

    private Brand brand;


    private Integer brandModelCode;


    private BrandModel brandModel;


    private LocalDate productionYear;


    private Integer maxSpeed;


    private Integer horsePower;


    private String rgbCode;


    private String description;


    private Integer categoryCode;

    private LookupCode category;


    private Integer bookingStatusCode;

    private LookupCode bookingStatus;


    private Float price;


    private String imgUrl;


}
