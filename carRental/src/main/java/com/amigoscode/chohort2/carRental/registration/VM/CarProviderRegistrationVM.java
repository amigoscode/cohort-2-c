package com.amigoscode.chohort2.carRental.registration.VM;


import com.amigoscode.chohort2.carRental.carProvider.VM.CarProviderVM;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString (callSuper = true)
public class CarProviderRegistrationVM extends UserRegistrationVM {



    @NotNull
    @ToString.Exclude
    private CarProviderVM carProviderVM;



}
