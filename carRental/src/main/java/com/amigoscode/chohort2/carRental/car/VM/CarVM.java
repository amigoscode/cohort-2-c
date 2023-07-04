package com.amigoscode.chohort2.carRental.car.VM;

import com.amigoscode.chohort2.carRental.car.Car;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@Accessors(chain = true)
public class CarVM {

    @NotEmpty
    private UUID registrationNumber;

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
    private Integer bookingStatusCode;

    @NotEmpty
    private Float price;

    @NotEmpty
    private String imgUrl;

    @NotEmpty
    Boolean isVisible;

    public static Car vmToEntity(CarVM carVM){
        return new Car()
                .setRegistrationNumber(carVM.registrationNumber)
                .setBrandCode(carVM.brandCode)
                .setBrandModelCode(carVM.brandModelCode)
                .setProductionYear(carVM.productionYear)
                .setMaxSpeed(carVM.maxSpeed)
                .setHorsePower(carVM.horsePower)
                .setRgbCode(carVM.rgbCode)
                .setDescription(carVM.description)
                .setCategoryCode(carVM.categoryCode)
                .setBookingStatusCode(carVM.bookingStatusCode)
                .setPrice(carVM.price)
                .setImgUrl(carVM.imgUrl)
                .setIsVisible(carVM.isVisible);
    }
}
