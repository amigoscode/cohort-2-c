package com.amigoscode.chohort2.carRental.lookupCode;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lookup-codes")
public class LookupCodeResource {

    private final LookupCodeService lookupCodeService;

    @GetMapping("/by-key/{key}")
    public ResponseEntity<List<LookupCode>>getAllByKey(@PathVariable String key){

        return ResponseEntity.ok(lookupCodeService.getAllByKey(key));
    }

    @GetMapping("/by-key-and-code/{key}/{code}")
    public ResponseEntity<LookupCode>getByKeyAndCode(@PathVariable String key ,@PathVariable Integer code){
        return ResponseEntity.ok(lookupCodeService.getByKeyAndCode(key,code));
    }

}
