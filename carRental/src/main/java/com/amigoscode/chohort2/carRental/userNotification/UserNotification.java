package com.amigoscode.chohort2.carRental.userNotification;

import com.amigoscode.chohort2.carRental.lookupCode.LookupCode;
import com.amigoscode.chohort2.carRental.lookupCode.LookupCodes;
import com.amigoscode.chohort2.carRental.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_notification")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserNotification {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "user_id", updatable = false, insertable = false)
    private User user;

    @Column (name = "message", nullable = false)
    private String message;

    @Column (name = "medium_code", nullable = false)
    private Integer mediumCode;

    @JoinColumnsOrFormulas({
            @JoinColumnOrFormula(formula = @JoinFormula(value = "'"+ LookupCodes.MediumCode.KEY +"'", referencedColumnName = "lookup_key")),
            @JoinColumnOrFormula(column = @JoinColumn(name = "medium_code", referencedColumnName = "code", insertable = false, updatable = false))
    })
    @OneToOne
    private LookupCode medium;

    @Column (name = "is_sent", nullable = false)
    private Boolean isSent;

    @Column (name = "sent_date")
    private LocalDateTime sentDate;

}
