package com.amigoscode.chohort2.carRental.driverLicense;

import com.amigoscode.chohort2.carRental.abstracts.AbstractAuditingEntity;
import com.amigoscode.chohort2.carRental.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Entity
@Table(name = "driver_license")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Where(clause = "is_deleted = false")
public class DriverLicense extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "user_id", nullable = false)
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "user_id", updatable = false,insertable = false)
    private User user;

    @Column (name = "number", nullable = false)
    private String driverLicenseNumber;

    @Column (name = "issued_in", nullable = false)
    private Long issuedIn;

    @Column (name = "issued_at", nullable = false)
    private LocalDate issuedAt;

    @Column (name = "expired_date", nullable = false)
    private LocalDate expiredDate;


}
