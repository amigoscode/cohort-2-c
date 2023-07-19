package com.amigoscode.chohort2.carRental.car;

import com.amigoscode.chohort2.carRental.authority.AuthorityConstants;
import com.amigoscode.chohort2.carRental.car.VM.CarSearchByProviderUserVM;
import com.amigoscode.chohort2.carRental.car.VM.CarSearchVM;
import com.amigoscode.chohort2.carRental.car.VM.CarVM;
import com.amigoscode.chohort2.carRental.specification.CarSearchSpecification;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("api/v1/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping
    @Secured({AuthorityConstants.CAR_PROVIDER})
    public ResponseEntity<CarDTO> addCar(@RequestBody @Valid CarVM carVM) {
        return ResponseEntity.ok(carService.addCar(carVM));
    }

    @DeleteMapping("/{id}")
    @Secured({AuthorityConstants.CAR_PROVIDER})
    public ResponseEntity<Void> deleteCarById(@PathVariable Long id) {
        carService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable Long id) {
        return ResponseEntity.ok(carService.getCarById(id));
    }

    @PostMapping("/search")
    public ResponseEntity<Page<CarDTO>> searchCars(@RequestBody CarSearchVM carSearchVM, @PageableDefault Pageable pageable) {
        Specification<Car> carSpecification = carSearchVM instanceof CarSearchByProviderUserVM vm1
                ? CarSearchSpecification.carSearch(vm1) : CarSearchSpecification.carSearch(carSearchVM);

        return ResponseEntity.ok(carService.getSearchCars(carSpecification, pageable));
    }

    @PutMapping("/{carId}")
    @Secured({AuthorityConstants.CAR_PROVIDER})
    public ResponseEntity<CarDTO> updateCar(@PathVariable Long carId, @RequestBody @Valid CarVM carVM) {
        return ResponseEntity.ok(carService.update(carId, carVM));
    }

    @PostMapping(
            value = "{carId}/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Secured({AuthorityConstants.CAR_PROVIDER})
    public void uploadCarImage(
            @PathVariable("carId") Long carId,
            @RequestParam("file") MultipartFile file) {
        carService.uploadImage(carId, file);
    }

    @GetMapping(
            value = "{carId}/image",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    @Secured({AuthorityConstants.CAR_PROVIDER})
    public byte[] getOriginalUnresizedCarImage(
            @PathVariable("carId") Long carId) {
        return carService.getOriginalImage(carId);
    }

    @GetMapping(
            value = "{carId}/image-fallback",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public byte[] getOriginalCarImageFallBack(
            @PathVariable("carId") Long carId) {
        return carService.getResizedImage(carId);
    }

}