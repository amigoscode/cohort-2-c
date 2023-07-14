package com.amigoscode.chohort2.carRental.car.VM;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;


@Setter
@Getter
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CarSearchByProviderUserVM extends CarSearchVM {


    private Long providerUserId;

}
