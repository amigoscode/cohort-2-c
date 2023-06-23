package com.amigoscode.chohort2.carRental.carProvider;


import com.amigoscode.chohort2.carRental.abstracts.AbstractAuditingEntity;
import com.amigoscode.chohort2.carRental.car.Car;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "car_provider")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Where(clause = "is_deleted = false")
public class CarProvider extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column (name = "cr_number", nullable = false, unique = true)
    private String crNumber;

    @OneToMany
    @JoinColumn (name = "car_provider_id")
    private List<Car> cars = new ArrayList<>();
}
