package com.amigoscode.chohort2.carRental.car.VM;

import com.amigoscode.chohort2.carRental.car.Car;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private Integer brandCode;

    @NotNull
    private Integer brandModelCode;

    @NotNull
    private LocalDate productionYear;

    @NotNull
    @Min(220)
    private Integer maxSpeed;

    @NotNull
    private Integer horsePower;

    @NotEmpty
    private String rgbCode;

    @NotEmpty
    private String description;

    @NotNull
    private Integer categoryCode;

    @NotNull
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
