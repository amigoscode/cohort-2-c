package com.amigoscode.chohort2.carRental.car;

import com.amigoscode.chohort2.carRental.car.VM.CarVM;
import com.amigoscode.chohort2.carRental.carProviderUser.CarProviderUser;
import com.amigoscode.chohort2.carRental.carProviderUser.CarProviderUserService;
import com.amigoscode.chohort2.carRental.exception.ApiRequestException;
import com.amigoscode.chohort2.carRental.lookupCode.LookupCodeDTO;
import com.amigoscode.chohort2.carRental.user.User;
import com.amigoscode.chohort2.carRental.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.UUID;

import static com.amigoscode.chohort2.carRental.constants.ErrorConstants.CAR_PROVIDER_USER;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CarServiceTest {

    @Mock
    private CarRepository carRepository;
    @Mock
    private UserService userService;
    @Mock
    private CarProviderUserService carProviderUserService;
    @Mock
    private User user;
    @Mock
    CarProviderUser carProviderUser;

    @InjectMocks
    private CarService underTest;
    private CarVM passedVM;
    private CarDTO expectedResult;

    private final static CarMapper carMapper = CarMapper.INSTANCE;
    @BeforeEach
    void tearUp () {
        passedVM = new CarVM()
                .setBrandCode(1000)
                .setBrandModelCode(1001)
                .setProductionYear(LocalDate.ofEpochDay(2021-1-1))
                .setMaxSpeed(250)
                .setHorsePower(500)
                .setRgbCode("#00FF00")
                .setDescription("Some")
                .setCategoryCode(1)
                .setPrice(100f)
                .setImgUrl("http://some.url.aws-s3.com/car1/v1.1");
        expectedResult = new CarDTO();
        expectedResult
                .setId(1L)
                .setCarProviderId(1L)
                .setRegistrationNumber(UUID.randomUUID())
                .setBrandCode(1000)
                .setBrandModelCode(1001)
                .setProductionYear(LocalDate.ofEpochDay(2021-1-1))
                .setMaxSpeed(250)
                .setHorsePower(500)
                .setRgbCode("#00FF00")
                .setDescription("Some")
                .setCategoryCode(1)
                .setCategory(new LookupCodeDTO()
                        .setKey("car_category")
                        .setCode(1)
                        .setName("luxury"))
                .setBookingStatusCode(1)
                .setBookingStatus(new LookupCodeDTO()
                        .setKey("car_booking_status")
                        .setCode(1)
                        .setName("available"))
                .setPrice(100f)
                .setImgUrl("http://some.url.aws-s3.com/car1/v1.1")
                .setIsVisible(Boolean.TRUE);

    }

    @Test
    void given_carProviderExists_when_addCar_then_newCarAdded (){

        given(userService.getLoggedInUser()).willReturn(user);
        given(user.getId()).willReturn(1L);
        given(carProviderUserService.findCarProviderUserByUserId(anyLong())).willReturn(carProviderUser);
        given(carRepository.save(any(Car.class))).willReturn(carMapper.toEntity(expectedResult));
        CarDTO actualResult = underTest.addCar(passedVM);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void given_carProviderNotExist_when_addCar_then_throw (){
        String expectedExceptionKey = CAR_PROVIDER_USER;
        given(userService.getLoggedInUser())
                .willReturn(user);
        given(user.getId())
                .willReturn(1L);
        given(carProviderUserService.findCarProviderUserByUserId(anyLong()))
                .willThrow(new ApiRequestException(expectedExceptionKey));

        String resultException = assertThrows(ApiRequestException.class, ()-> underTest.addCar(passedVM)).getErrorKey();
        assertThat(resultException).isEqualTo(expectedExceptionKey);
    }

    @Test
    void delete() {
    }

    @Test
    void getCarById() {
    }

    @Test
    void getAllByProviderId() {
    }
}