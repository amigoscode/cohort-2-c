package com.amigoscode.chohort2.carRental.country;

import com.amigoscode.chohort2.carRental.city.City;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cc_code",nullable = false,unique = true)
    private String ccCode;

    @Column(name = "calling_code",nullable = false,unique = true)
    private String callingCode;


    @OneToMany(mappedBy = "country",fetch = FetchType.LAZY)
    private List<City> cities = new ArrayList<>();


}
