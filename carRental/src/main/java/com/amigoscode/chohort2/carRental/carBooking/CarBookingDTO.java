package com.amigoscode.chohort2.carRental.carBooking;

import com.amigoscode.chohort2.carRental.lookupCode.LookupCode;
import com.amigoscode.chohort2.carRental.lookupCode.LookupCodes;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@EqualsAndHashCode
public class CarBookingDTO {


    private Long id;


    private Long userId;


    private Long carUserId;


    private LocalDate checkInDate;


    private LocalDate checkOutDate;


    private Integer statusCode;


    private LookupCode status;

    private Float tax;

    private Float finalPrice;

}
