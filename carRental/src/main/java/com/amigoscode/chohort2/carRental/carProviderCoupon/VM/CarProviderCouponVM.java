package com.amigoscode.chohort2.carRental.carProviderCoupon.VM;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Setter
@Getter
@Accessors(chain = true)
public class CarProviderCouponVM {
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

}
