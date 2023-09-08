package com.amigoscode.chohort2.carRental.carProviderCoupon.VM;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
@Accessors(chain = true)
public class CarProviderCouponVM implements Serializable {

    private static final long serialVersionUID = 9178661439383356177L;
    @NotEmpty
    private String couponCode;
    @NotEmpty
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate startDate;
    @NotEmpty
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate endDate;
    @NotEmpty
    private Integer numOfUsePerUser;
    @NotEmpty
    private Boolean isAvailable = true;

    @NotNull
    @Min(0)
    private Float discountPercentage;



}
