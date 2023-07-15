package com.amigoscode.chohort2.carRental.abstracts;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@MappedSuperclass
@Setter
@Getter
@Accessors(chain = true)
public abstract class AbstractAddress extends AbstractAuditingEntity {



    @Column(name = "city_id")
    protected Long cityId;

    @Column(name = "location")
    protected String location;

    @Column(name = "postal_code")
    protected String postalCode;




}
