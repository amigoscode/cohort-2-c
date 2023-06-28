package com.amigoscode.chohort2.carRental.carBooking;

import com.amigoscode.chohort2.carRental.abstracts.AbstractAuditingEntity;
import com.amigoscode.chohort2.carRental.carUser.CarUser;
import com.amigoscode.chohort2.carRental.lookupCode.LookupCode;
import com.amigoscode.chohort2.carRental.lookupCode.LookupCodes;
import com.amigoscode.chohort2.carRental.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Entity
@Table(name = "car_booking")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Where(clause = "is_deleted = false")
public class CarBooking extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id",nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",updatable = false,insertable = false)
    private User user;

    @Column(name = "car_user_id",nullable = false)
    private Long carUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_user_id",updatable = false,insertable = false)
    private CarUser carUser;

    @Column(name = "check_in_date",nullable = false)
    private LocalDate checkInDate;

    @Column(name = "check_out_date",nullable = false)
    private LocalDate checkOutDate;

    @Column(name = "status_code",nullable = false)
    private Integer statusCode;


    @JoinColumnsOrFormulas({
            @JoinColumnOrFormula(formula = @JoinFormula(value = "'"+ LookupCodes.UserBookingStatus.KEY +"'", referencedColumnName = "lookup_key")),
            @JoinColumnOrFormula(column = @JoinColumn(name = "status_code", referencedColumnName = "code", insertable = false, updatable = false))
    })
    @OneToOne
    private LookupCode status;

    private Float tax;

    private Float finalPrice;





}
