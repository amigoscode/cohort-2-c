package com.amigoscode.chohort2.carRental.user;

import com.amigoscode.chohort2.carRental.abstracts.AbstractAuditingEntity;
import com.amigoscode.chohort2.carRental.authority.Authority;
import com.amigoscode.chohort2.carRental.lookupCode.LookupCode;
import com.amigoscode.chohort2.carRental.lookupCode.LookupCodes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cr_user")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true,length = 50)
    private String username;

    @Column(name = "first_name", nullable = false,length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false,length = 50)
    private String lastName;

    @Column(name = "email", nullable = false,unique = true)
    private String email;

    @Column(name = "nin", nullable = false,unique = true)
    private String nin;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "type_code",nullable = false)
    private Integer typeCode;

    @JoinColumnsOrFormulas({
            @JoinColumnOrFormula(formula = @JoinFormula(value = "'"+ LookupCodes.UserType.KEY +"'", referencedColumnName = "lookup_key")),
            @JoinColumnOrFormula(column = @JoinColumn(name = "type_code", referencedColumnName = "code", insertable = false, updatable = false))
    })
    @OneToOne
    private LookupCode type;

    @Column(name = "status_code",nullable = false)
    private Integer statusCode;

    @JoinColumnsOrFormulas({
            @JoinColumnOrFormula(formula = @JoinFormula(value = "'"+ LookupCodes.UserStatus.KEY +"'", referencedColumnName = "lookup_key")),
            @JoinColumnOrFormula(column = @JoinColumn(name = "type_code", referencedColumnName = "code", insertable = false, updatable = false))
    })
    @OneToOne
    private LookupCode status;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name")}

    )
    private Set<Authority> authorities = new HashSet<>();



}
