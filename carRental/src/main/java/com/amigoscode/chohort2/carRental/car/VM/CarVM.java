package com.amigoscode.chohort2.carRental.car.VM;

import com.amigoscode.chohort2.carRental.car.Car;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
@Accessors(chain = true)
public class CarVM implements Serializable {

    @Serial
    private static final long serialVersionUID = -1798070786993154676L;

    @NotEmpty
    private Integer brandCode;

    @NotEmpty
    private Integer brandModelCode;

    @NotEmpty
    private LocalDate productionYear;

    @NotEmpty
    private Integer maxSpeed;

    @NotEmpty
    private Integer horsePower;

    @NotEmpty
    private String rgbCode;

    @NotEmpty
    private String description;

    @NotEmpty
    private Integer categoryCode;

    @NotEmpty
    private Float price;

    @NotEmpty
    private String imgUrl;


    public static Car vmToEntity(CarVM carVM){
        return new Car()
                .setBrandCode(carVM.brandCode)
                .setBrandModelCode(carVM.brandModelCode)
                .setProductionYear(carVM.productionYear)
                .setMaxSpeed(carVM.maxSpeed)
                .setHorsePower(carVM.horsePower)
                .setRgbCode(carVM.rgbCode)
                .setDescription(carVM.description)
                .setCategoryCode(carVM.categoryCode)
                .setPrice(carVM.price)
                .setImgUrl(carVM.imgUrl);
    }
}
