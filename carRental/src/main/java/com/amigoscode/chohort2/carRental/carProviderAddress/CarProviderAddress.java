package com.amigoscode.chohort2.carRental.carProviderAddress;

import com.amigoscode.chohort2.carRental.abstracts.AbstractAuditingEntity;
import com.amigoscode.chohort2.carRental.carProvider.CarProvider;
import com.amigoscode.chohort2.carRental.city.City;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "car_provider_address")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Where(clause = "is_deleted = false")
public class CarProviderAddress extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "car_provider_id", nullable = false)
    private Long carProviderId;

    @ManyToOne (fetch = FetchType.LAZY,optional = false)
    @JoinColumn (name = "car_provider_id", updatable = false, insertable = false)
    private CarProvider carProvider;

    @Column (name = "city_id", nullable = false)
    private Long cityId;

    @ManyToOne (fetch = FetchType.LAZY,optional = false)
    @JoinColumn (name = "city_id", updatable = false, insertable = false)
    private City city;

    @Column (name = "location", nullable = false)
    private String location;

    @Column (name = "postal_code", nullable = false)
    private String postalCode;



}
