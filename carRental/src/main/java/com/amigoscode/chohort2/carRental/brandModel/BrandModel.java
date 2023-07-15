package com.amigoscode.chohort2.carRental.brandModel;

import com.amigoscode.chohort2.carRental.brand.Brand;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "brand_model")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class BrandModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "brand_id", nullable = false)
    private Long brandId;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "brand_id", updatable = false,insertable = false)
    private Brand brand;

    @Column (name = "code", nullable = false)
    private Integer code;

    @Column (name = "name", nullable = false)
    private String name;


}
