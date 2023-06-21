package com.amigoscode.chohort2.carRental.authority;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Entity
@Table(name = "authority")
@Setter
@Getter
@Accessors(chain = true)
public class Authority {

    @Id
    private String name;

    @Column(name = "full_name")
    private String fullName;


}
