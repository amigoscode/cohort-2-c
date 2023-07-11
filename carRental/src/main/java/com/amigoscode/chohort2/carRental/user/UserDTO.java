package com.amigoscode.chohort2.carRental.user;

import com.amigoscode.chohort2.carRental.lookupCode.LookupCodeDTO;
import com.amigoscode.chohort2.carRental.security.SecurityUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@ToString
@EqualsAndHashCode
public class UserDTO {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String nin;

    private Integer typeCode;

    private LookupCodeDTO type;

    private Integer statusCode;

    private LookupCodeDTO status;
    @ToString.Include
    private String email(){
        return SecurityUtils.maskEmail(email);
    }

}
