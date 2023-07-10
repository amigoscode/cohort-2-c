package com.amigoscode.chohort2.carRental.carProviderCoupon;

import com.amigoscode.chohort2.carRental.carProvider.CarProviderDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link CarProviderCoupon}
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString
@EqualsAndHashCode
public class CarProviderCouponDTO implements Serializable {
    private Long id;
    private Long carProviderId;
    private CarProviderDTO carProvider;
    private String couponCode;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer numOfUsePerUser;
    private Boolean isAvailable;
}