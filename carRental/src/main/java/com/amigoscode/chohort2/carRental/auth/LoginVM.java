package com.amigoscode.chohort2.carRental.auth;

import com.amigoscode.chohort2.carRental.security.SecurityUtils;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginVM implements Serializable {
    private String username;
    private String password;
    @ToString.Include
    private String password(){
        return SecurityUtils.obfuscateString(password);
    }
}
