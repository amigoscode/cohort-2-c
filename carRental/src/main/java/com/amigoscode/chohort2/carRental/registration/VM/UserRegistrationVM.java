package com.amigoscode.chohort2.carRental.registration.VM;


import com.amigoscode.chohort2.carRental.user.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Setter
@Getter
@Accessors(chain = true)
public abstract class UserRegistrationVM implements Serializable {

    @NotEmpty
    private String username;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String email;

    @NotEmpty
    private String nin;

    @NotEmpty
    @ToString.Exclude
    private String password;


    public static User vmToEntity(UserRegistrationVM userRegistrationVM){
        return new User()
                .setUsername(userRegistrationVM.username)
                .setFirstName(userRegistrationVM.firstName)
                .setLastName(userRegistrationVM.lastName)
                .setEmail(userRegistrationVM.email)
                .setNin(userRegistrationVM.nin);
    }


}
