package com.amigoscode.chohort2.carRental.car;

import com.amigoscode.chohort2.carRental.car.VM.CarVM;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cars")
@RequiredArgsConstructor
public class CarController {
        private final Logger log = LoggerFactory.getLogger(CarController.class);
        private final CarService carService;

        @PostMapping
        public ResponseEntity<CarDTO> addCar (@RequestBody @Valid CarVM carVM){
            return ResponseEntity.ok(carService.addCar(carVM));
        }
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteCarById (@PathVariable Long id) {
            carService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        @GetMapping("/{id}")
        public ResponseEntity<CarDTO>getCarById (@PathVariable Long id) {
            return ResponseEntity.ok(carService.findById(id));
        }
        @GetMapping("/{providerId}")
        public ResponseEntity<List<CarDTO>> getAllByProvider(@PathVariable Long providerId){
            return ResponseEntity.ok(carService.getAllByProviderId(providerId));
        }
}
