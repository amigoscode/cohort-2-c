package com.amigoscode.chohort2.carRental.userAddress;

import com.amigoscode.chohort2.carRental.abstracts.AbstractAddress;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;

@Entity
@Setter
@Getter
@Accessors(chain = true)
@Where(clause = "is_deleted = false")
public class UserAddress extends AbstractAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;


}
