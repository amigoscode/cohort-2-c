package com.amigoscode.chohort2.carRental.carProviderUser;

import com.amigoscode.chohort2.carRental.carProvider.CarProviderDTO;
import com.amigoscode.chohort2.carRental.user.UserDTO;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link CarProviderUser}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CarProviderUserDTO implements Serializable {
    private Long id;
    private Long userId;
    private UserDTO user;
    private Long carProviderId;
    private CarProviderDTO carProvider;
    private Boolean companyAdmin;
}