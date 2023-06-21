package com.amigoscode.chohort2.carRental.lookupCode;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LookupCodeService {

    private final LookupCodeRepository lookupCodeRepository;


    public List<LookupCode> getAllByKey(String key) {
        return lookupCodeRepository.findAllByKey(key);
    }

    public List<LookupCode> findAll() {
        return lookupCodeRepository.findAll();
    }

    public LookupCode getByKeyAndCode(String key, Integer code) {
        return lookupCodeRepository
                .findByKeyAndCode(key, code)
                .orElse(LookupCode.getDefault());
    }


}
