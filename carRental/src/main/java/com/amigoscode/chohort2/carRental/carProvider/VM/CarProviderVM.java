package com.amigoscode.chohort2.carRental.carProvider.VM;

import com.amigoscode.chohort2.carRental.carProvider.CarProvider;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class CarProviderVM {


    @NotEmpty
    private String name;

    @NotEmpty
    private String crNumber;


    public static CarProvider vmToEntity(CarProviderVM carProviderVM) {
        return new CarProvider().setName(carProviderVM.name).setCrNumber(carProviderVM.crNumber);
    }


}
