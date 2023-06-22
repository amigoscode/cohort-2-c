package com.amigoscode.chohort2.carRental.brandModel;

import com.amigoscode.chohort2.carRental.brand.Brand;
import com.amigoscode.chohort2.carRental.brand.BrandDTO;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class BrandModelDTO {

    private Long id;

    private Integer brandId;

    private BrandDTO brand;

    private Integer code;


}
