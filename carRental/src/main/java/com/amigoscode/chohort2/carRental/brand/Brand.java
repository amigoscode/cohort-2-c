package com.amigoscode.chohort2.carRental.brand;

import com.amigoscode.chohort2.carRental.brandModel.BrandModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "brand")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Brand {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, unique = true)
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private String code;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany (mappedBy = "brand")
    private Set<BrandModel>brandModels = new HashSet<>();
}
