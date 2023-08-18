package com.amigoscode.chohort2.carRental.carBooking.vm;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class UserBookingVM {

    @NotNull
    private Long carId;
    @NotNull
    private LocalDate checkIn;
    @NotNull
    private LocalDate checkOut;

    private String couponCode;


}
