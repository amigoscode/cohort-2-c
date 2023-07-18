package com.amigoscode.chohort2.carRental.carProviderCoupon.VM;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
@Setter
@Getter
@ToString
@Accessors(chain = true)
public class CarProviderCouponAvailabilityVM implements Serializable {

    @NotEmpty
    private Boolean isAvailable;

}
