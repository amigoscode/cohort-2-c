package com.amigoscode.chohort2.carRental.car;

import com.amigoscode.chohort2.carRental.annotation.TransactionalService;
import com.amigoscode.chohort2.carRental.car.VM.CarVM;
import com.amigoscode.chohort2.carRental.carProviderUser.CarProviderUserService;
import com.amigoscode.chohort2.carRental.constants.ErrorConstants;
import com.amigoscode.chohort2.carRental.exception.ApiRequestException;
import com.amigoscode.chohort2.carRental.external.s3.S3Service;
import com.amigoscode.chohort2.carRental.image.ImageHandler;
import com.amigoscode.chohort2.carRental.lookupCode.LookupCodes;
import com.amigoscode.chohort2.carRental.external.s3.S3Buckets;
import com.amigoscode.chohort2.carRental.user.UserService;
import com.amigoscode.chohort2.carRental.validation.Validator;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@TransactionalService
@RequiredArgsConstructor
public class CarService extends ImageHandler<Car, Long> {

    private final CarRepository carRepository;
    private final CarProviderUserService carProviderUserService;
    private final UserService userService;
    private final S3Buckets s3Buckets;
    private final S3Service s3Service;


    public CarDTO addCar(CarVM carVM) {

        return CarMapper.INSTANCE.toDto(createCar(carVM));
    }

    public void delete(Long id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new ApiRequestException(ErrorConstants.CAR_NOT_FOUND));

        Validator.invalidateIfFalse(() -> car.getCarProviderId().equals(getCurrentCarProviderId()),
                ErrorConstants.CAR_PROVIDER_USER,
                "car doesn't belong to the provider");
        carRepository.delete(car);
    }

    public CarDTO update(Long carId, CarVM carVM) {
        return CarMapper.INSTANCE.toDto(updateCar(carId, carVM));
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

    private Car updateCar(Long carId, CarVM carVM) {
        Long providerId = getCurrentCarProviderId();
        Car car = carRepository.findById(carId).orElseThrow(() -> new ApiRequestException(ErrorConstants.CAR_NOT_FOUND));
        Validator.invalidateIfFalse(() -> car.getCarProviderId().equals(providerId),
                ErrorConstants.CAR_PROVIDER_USER,
                "car doesn't belong to the provider");
        Car carUpToDate = CarVM.vmToEntity(carVM);

        carUpToDate
                .setId(car.getId())
                .setCarProviderId(providerId)
                .setRegistrationNumber(car.getRegistrationNumber())
                .setBookingStatusCode(car.getBookingStatusCode())
                .setIsVisible(car.getIsVisible());

        return carRepository.merge(carUpToDate);
    }


    private Long getCurrentCarProviderId() {
        Long id = userService.getLoggedInUser().getId();
        return carProviderUserService.findCarProviderUserByUserId(id).getCarProviderId();
    }


    public Page<CarDTO> getSearchCars(Specification<Car> carSearch, Pageable pageable) {
        return carRepository.findAll(carSearch, pageable)
                .map(CarMapper.INSTANCE::toDto);
    }

    public void uploadImage(Long carId, MultipartFile file) {
        Car car = getCarIfBelongsToCurrentProviderOrThrow(carId);
        uploadImage(carId, car, file);
        carRepository.save(car);
    }

    public byte[] getOriginalUnresizedImage(Long carId) {
        Car car = getCarIfBelongsToCurrentProviderOrThrow(carId);
        return getOriginalUnresizedImage(carId, car);
    }


    public byte[] getOriginalResizedImage(Long carId) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new ApiRequestException(ErrorConstants.CAR_NOT_FOUND));
        return getOriginalResizedImage(carId, car);
    }

    public Car getCarIfBelongsToCurrentProviderOrThrow(Long id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new ApiRequestException(ErrorConstants.CAR_NOT_FOUND));

        Validator.invalidateIfFalse(() -> car.getCarProviderId().equals(getCurrentCarProviderId()),
                ErrorConstants.CAR_PROVIDER_USER,
                "car doesn't belong to the provider");

        return car;
    }

    @Override
    protected void setImgUrl(Car car, String imgUrl) {
        car.setImgUrl(imgUrl);
    }

    @Override
    protected String getImgUrl(Car car) {
        return car.getImgUrl();
    }

    @Override
    protected S3Buckets getBuckets() {
        return s3Buckets;
    }

    @Override
    protected S3Service getS3Service() {
        return s3Service;
    }


}
