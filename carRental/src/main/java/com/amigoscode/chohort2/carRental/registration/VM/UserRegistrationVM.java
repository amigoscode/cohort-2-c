package com.amigoscode.chohort2.carRental.registration.VM;


import com.amigoscode.chohort2.carRental.user.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Setter
@Getter
@Accessors(chain = true)
public  class UserRegistrationVM implements Serializable {

    @NotEmpty
    protected String username;

    @NotEmpty
    protected String firstName;

    @NotEmpty
    protected String lastName;

    @NotEmpty
    protected String email;

    @NotEmpty
    protected String nin;

    @NotEmpty
    @ToString.Exclude
    protected String password;


    public static User vmToEntity(UserRegistrationVM userRegistrationVM){
        return new User()
                .setUsername(userRegistrationVM.username)
                .setFirstName(userRegistrationVM.firstName)
                .setLastName(userRegistrationVM.lastName)
                .setEmail(userRegistrationVM.email)
                .setNin(userRegistrationVM.nin);
    }


}
