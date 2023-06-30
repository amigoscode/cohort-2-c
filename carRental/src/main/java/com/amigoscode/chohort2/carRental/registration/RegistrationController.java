package com.amigoscode.chohort2.carRental.registration;

import com.amigoscode.chohort2.carRental.registration.VM.CarProviderRegistrationVM;
import com.amigoscode.chohort2.carRental.registration.VM.ClientRegistrationVM;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/registrations")
public class RegistrationController {

    private final Logger log = LoggerFactory.getLogger(RegistrationController.class);

    private final RegistrationService registrationService;

    @PostMapping("clients")
    public ResponseEntity<Void> clientRegistration(@RequestBody @Valid ClientRegistrationVM clientRegistrationVM) {

        log.info("user registration info {}", clientRegistrationVM);

        registrationService.clientRegistration(clientRegistrationVM);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("clients")
    public ResponseEntity<Void> clientRegistration(@RequestBody @Valid CarProviderRegistrationVM carProviderRegistrationVM) {

        log.info("car provider user registration info {}", carProviderRegistrationVM);

        registrationService.carProviderRegistration(carProviderRegistrationVM);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
