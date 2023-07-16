package com.amigoscode.chohort2.carRental.car;

import com.amigoscode.chohort2.carRental.abstracts.AbstractAuditingEntity;
import com.amigoscode.chohort2.carRental.brand.Brand;
import com.amigoscode.chohort2.carRental.brandModel.BrandModel;
import com.amigoscode.chohort2.carRental.carProvider.CarProvider;
import com.amigoscode.chohort2.carRental.lookupCode.LookupCode;
import com.amigoscode.chohort2.carRental.lookupCode.LookupCodes;
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
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "car")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Where(clause = "is_deleted = false")
public class Car extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "car_provider_id", nullable = false)
    private  Long carProviderId;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "car_provider_id", updatable = false,insertable = false)
    private CarProvider carProvider;

    @Column (name = "registration_number", nullable = false)
    private UUID registrationNumber;

    @Column (name = "brand_code", nullable = false)
    private Integer brandCode;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "brand_code", updatable = false,insertable = false,referencedColumnName = "code")
    private Brand brand;

    @Column (name = "brand_model_code", nullable = false)
    private Integer brandModelCode;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "brand_model_code", updatable = false,insertable = false,referencedColumnName = "code")
    private BrandModel brandModel;

    @Column (name = "production_year", nullable = false)
    private LocalDate productionYear;

    @Column (name = "max_speed", nullable = false)
    private Integer maxSpeed;

    @Column (name = "horse_power", nullable = false)
    private Integer horsePower;

    @Column (name = "rgb_code", nullable = false)
    private String rgbCode;

    @Column (name = "description", nullable = false)
    private String description;

    @Column (name = "category_code", nullable = false)
    private Integer categoryCode;

    @JoinColumnsOrFormulas({
            @JoinColumnOrFormula(formula = @JoinFormula(value = "'"+ LookupCodes.CarCategory.KEY +"'", referencedColumnName = "lookup_key")),
            @JoinColumnOrFormula(column = @JoinColumn(name = "category_code", referencedColumnName = "code", insertable = false, updatable = false))
    })
    @OneToOne
    private LookupCode category;

    @Column (name = "booking_status_code", nullable = false)
    private Integer bookingStatusCode;

    @JoinColumnsOrFormulas({
            @JoinColumnOrFormula(formula = @JoinFormula(value = "'"+ LookupCodes.CarBookingStatus.KEY +"'", referencedColumnName = "lookup_key")),
            @JoinColumnOrFormula(column = @JoinColumn(name = "booking_status_code", referencedColumnName = "code", insertable = false, updatable = false))
    })
    @OneToOne
    private LookupCode bookingStatus;

    @Column (name = "price", nullable = false)
    private Float price;

    @Column (name = "img_url", nullable = false)
    private String imgUrl;

    @Column (name = "is_visible", nullable = false)
    private Boolean isVisible;

}
