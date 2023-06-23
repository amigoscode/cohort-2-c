package com.amigoscode.chohort2.carRental.car;

import com.amigoscode.chohort2.carRental.brand.BrandDTO;
import com.amigoscode.chohort2.carRental.brandModel.BrandModelDTO;
import com.amigoscode.chohort2.carRental.carProvider.CarProviderDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
@Accessors(chain = true)
public class CarDTO {

    private Long id;

    private Long carProviderId;

    private CarProviderDTO carProviderDTO;

    private String uuid;

    private Integer brandCode;

    private BrandDTO brand;

    private Integer brandModelCode;

    private BrandModelDTO brandModel;

    private LocalDate productionYear;

    private Integer maxSpeed;

    private Integer horsePower;

    private String description;

    private Integer categoryCode;

    private Integer bookingStatusCode;

    private Float price;

    private String imgUrl;

    private Boolean isVisible;


}
