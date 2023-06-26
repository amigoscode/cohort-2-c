package com.amigoscode.chohort2.carRental.carProviderCoupon;

import com.amigoscode.chohort2.carRental.abstracts.AbstractAuditingEntity;
import com.amigoscode.chohort2.carRental.carProvider.CarProvider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Entity
@Table(name = "car_provider_coupon")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Where(clause = "is_deleted = false")
public class CarProviderCoupon extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "car_provider_id", nullable = false)
    private Long carProviderId;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "car_provider_id", insertable = false, updatable = false)
    private CarProvider carProvider;

    @Column (name = "coupon_code", nullable = false, unique = true)
    private String couponCode;

    @Column (name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column (name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column (name = "num_of_user_per_user", nullable = false)
    private Integer numOfUsePerUser;

    @Column (name = "is_available", nullable = false)
    private Boolean isAvailable = true;


}
