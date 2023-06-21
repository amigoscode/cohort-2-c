package com.amigoscode.chohort2.carRental.lookupCode;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LookupCodeService {

    private final LookupCodeRepository lookupCodeRepository;


    public List<LookupCode> getAllLookupCodesByKey(String key) {
        return lookupCodeRepository.findAllByKey(key);
    }

    public List<LookupCode> findAllLookupCodes() {
        return lookupCodeRepository.findAll();
    }

    public LookupCode getLookupCodeByKeyAndCode(String key, Integer code) {
        return lookupCodeRepository
                .findByKeyAndCode(key, code)
                .orElse(LookupCode.getDefault());
    }


}
