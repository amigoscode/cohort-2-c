package com.amigoscode.chohort2.carRental.carProviderUser;

import com.amigoscode.chohort2.carRental.abstracts.AbstractAuditingEntity;
import com.amigoscode.chohort2.carRental.carProvider.CarProvider;
import com.amigoscode.chohort2.carRental.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "car_provider_user")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Where(clause = "is_deleted = false")
public class CarProviderUser extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "user_id", nullable = false)
    private Long userId;

    @OneToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "user_id", updatable = false, insertable = false)
    private User user;

    @Column (name = "car_provider_id", nullable = false)
    private Long carProviderId;

    @OneToOne (fetch = FetchType.LAZY,optional = false)
    @JoinColumn (name = "car_provider_id", updatable = false, insertable = false)
    private CarProvider carProvider;

    @Column (name = "company_admin", nullable = false)
    private Boolean companyAdmin;
}

