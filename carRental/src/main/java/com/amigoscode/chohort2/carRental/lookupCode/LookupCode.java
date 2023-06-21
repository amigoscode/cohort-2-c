package com.amigoscode.chohort2.carRental.lookupCode;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "lookup_code")
@Setter
@Getter
public class LookupCode implements Serializable {


    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lookup_key", nullable = false)
    private String key;

    @Column(name = "code", nullable = false)
    private Integer code;

    @Column(name = "name", nullable = false)
    private String name;


    public static LookupCode getDefault() {
        LookupCode lookupCode = new LookupCode();
        lookupCode.setKey("default_key");
        lookupCode.setCode(0);
        lookupCode.setName("not found");


        return lookupCode;
    }

}
