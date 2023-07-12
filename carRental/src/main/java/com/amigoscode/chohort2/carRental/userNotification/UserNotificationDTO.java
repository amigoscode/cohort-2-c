package com.amigoscode.chohort2.carRental.userNotification;

import com.amigoscode.chohort2.carRental.lookupCode.LookupCodeDTO;
import com.amigoscode.chohort2.carRental.user.UserDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link UserNotification}
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString
@EqualsAndHashCode
public class UserNotificationDTO implements Serializable {
    private Long id;
    private Long userId;
//    private UserDTO user;
    private String message;
    private Integer mediumCode;
    private LookupCodeDTO medium;
    private Boolean isSent;
    private LocalDateTime sentDate;
}