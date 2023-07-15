package com.amigoscode.chohort2.carRental.car;

import com.amigoscode.chohort2.carRental.annotation.TransactionalService;
import com.amigoscode.chohort2.carRental.car.VM.CarVM;
import com.amigoscode.chohort2.carRental.carProviderUser.CarProviderUserService;
import com.amigoscode.chohort2.carRental.constants.ErrorConstants;
import com.amigoscode.chohort2.carRental.exception.ApiRequestException;
import com.amigoscode.chohort2.carRental.lookupCode.LookupCodes;
import com.amigoscode.chohort2.carRental.user.UserService;
import com.amigoscode.chohort2.carRental.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;


@TransactionalService
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    private final CarProviderUserService carProviderUserService;
    private final UserService userService;


    public CarDTO addCar(CarVM carVM) {

        return CarMapper.INSTANCE.toDto(createCar(carVM));
    }

    public void delete(Long id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new ApiRequestException(ErrorConstants.CAR_NOT_FOUND));

        Validator.invalidateIfFalse(() -> car.getCarProviderId().equals(getCurrentCarProviderId()),
                ErrorConstants.CAR_PROVIDER_USER,
                "car doesn't belong to the provider");
        carRepository.deleteById(id);
    }

    public CarDTO getCarById(Long id) {
        return carRepository.findById(id)
                .map(CarMapper.INSTANCE::toDto)
                .orElseThrow(() -> new ApiRequestException(ErrorConstants.CAR_NOT_FOUND));
    }



    private Car createCar(CarVM carVM) {
        Long id = getCurrentCarProviderId();
        Car car = CarVM.vmToEntity(carVM);
        car.setCarProviderId(id);
        car.setIsVisible(true);
        car.setBookingStatusCode(LookupCodes.CarBookingStatus.available);
        car.setRegistrationNumber(UUID.randomUUID());
        return carRepository.save(car);
    }

    private Long getCurrentCarProviderId() {
        Long id = userService.getLoggedInUser().getId();
        return carProviderUserService.findCarProviderUserByUserId(id).getCarProviderId();
    }


    public Page<CarDTO> getSearchCars(Specification<Car> carSearch, Pageable pageable) {
        return carRepository.findAll(carSearch,pageable)
                .map(CarMapper.INSTANCE::toDto);
    }
}
