package com.amigoscode.chohort2.carRental.car;

import com.amigoscode.chohort2.carRental.authority.AuthorityConstants;
import com.amigoscode.chohort2.carRental.car.VM.CarSearchByProviderUserVM;
import com.amigoscode.chohort2.carRental.car.VM.CarVM;
import com.amigoscode.chohort2.carRental.car.VM.CarSearchVM;
import com.amigoscode.chohort2.carRental.specification.CarSearchSpecification;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cars")
@RequiredArgsConstructor
public class CarController {

        private final CarService carService;

        @PostMapping
        @Secured({AuthorityConstants.CAR_PROVIDER})
        public ResponseEntity<CarDTO> addCar (@RequestBody @Valid CarVM carVM){
            return ResponseEntity.ok(carService.addCar(carVM));
        }
        @DeleteMapping("/{id}")
        @Secured({AuthorityConstants.CAR_PROVIDER})
        public ResponseEntity<Void> deleteCarById (@PathVariable Long id) {
            carService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        @GetMapping("/{id}")
        public ResponseEntity<CarDTO>getCarById (@PathVariable Long id) {
            return ResponseEntity.ok(carService.getCarById(id));
        }
        @PostMapping("/search")
        public  ResponseEntity<Page<CarDTO>> searchCars(@RequestBody CarSearchVM vm, @PageableDefault Pageable pageable){
            Specification<Car> carSpecification;
            if (vm instanceof CarSearchByProviderUserVM vm1) carSpecification = CarSearchSpecification.carSearch(vm1);
            else carSpecification = CarSearchSpecification.carSearch(vm);

// Preview feature, only with java 21 available;
//
//            Specification<Car> carSpecification = CarSearchSpecification.carSearch(
//                    switch (vm) {
//                        case CarSearchByProviderUserVM vm1 -> vm1;
//                        default -> vm;
//                    }
//            );

            return ResponseEntity.ok(carService.getSearchCars(carSpecification, pageable));
        }

        @PutMapping
        @Secured({AuthorityConstants.CAR_PROVIDER})
        public ResponseEntity<CarDTO>updateCar (@RequestBody @Valid CarVM carVM){
            return ResponseEntity.ok(carService.update(carVM));
        }
}
