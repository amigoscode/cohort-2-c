package com.amigoscode.chohort2.carRental.brand;

import com.amigoscode.chohort2.carRental.abstracts.EntityMapper;
import com.amigoscode.chohort2.carRental.brandModel.BrandModel;
import com.amigoscode.chohort2.carRental.brandModel.BrandModelDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Accessors(chain = true)
public class BrandDTO {

    private Long id;
    private Integer code;
    private String name;
    private List<BrandModelDTO> brandModels = new ArrayList<>();

}
