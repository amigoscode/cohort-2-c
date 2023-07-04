package com.amigoscode.chohort2.carRental.car;

import com.amigoscode.chohort2.carRental.annotation.TransactionalService;
import com.amigoscode.chohort2.carRental.car.VM.CarVM;
import com.amigoscode.chohort2.carRental.carProviderUser.CarProviderUserService;
import com.amigoscode.chohort2.carRental.constants.ErrorConstants;
import com.amigoscode.chohort2.carRental.exception.ApiRequestException;
import com.amigoscode.chohort2.carRental.user.UserService;
import com.amigoscode.chohort2.carRental.validation.Validator;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@TransactionalService
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final static CarMapper carMapper = CarMapper.INSTANCE;
    private final CarProviderUserService carProviderUserService;
    private final UserService userService;


    public CarDTO addCar(CarVM carVM) {

        return carMapper.toDto(createCar(carVM));
    }
    public void delete(Long id) {
        Car car = carRepository.findById(id).orElseThrow(()->new ApiRequestException(ErrorConstants.CAR_NOT_FOUND));

        Validator.invalidateIfFalse(()->car.getCarProviderId().equals(getCurrentCarProviderId()),
                ErrorConstants.CAR_PROVIDER_USER,
                "car doesn't belong to the user");
        carRepository.deleteById(id);
    }
    public CarDTO findById(Long id) {
        return carRepository.findById(id).map(carMapper::toDto).orElseThrow(()-> new ApiRequestException(ErrorConstants.CAR_NOT_FOUND));
    }
    public List<CarDTO> getAllByProviderId(Long providerId) {
        return carRepository.getCarsByCarProviderId(providerId).stream().map(carMapper::toDto).collect(Collectors.toList());
    }

    private Car createCar (CarVM carVM) {
        Long id = getCurrentCarProviderId();
        return carRepository.save(CarVM.vmToEntity(carVM).setCarProviderId(id));
    }

    private Long getCurrentCarProviderId () {
            Long id = userService.getLoggedInUser().getId();
            return carProviderUserService.findCarProviderUserByUserId(id).getCarProviderId();
        }

}
