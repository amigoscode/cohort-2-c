package com.amigoscode.chohort2.carRental.config.audit;

import com.amigoscode.chohort2.carRental.constants.Constants;
import com.amigoscode.chohort2.carRental.security.SecurityUtils;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;


public class SystemAudit implements AuditorAware<String> {


    @Override
    public Optional<String> getCurrentAuditor() {
        String auditBy = SecurityUtils
                .getCurrentUsernameOpt()
                .orElse(Constants.SYSTEM);

        return Optional.
                of(auditBy.equalsIgnoreCase(Constants.ANONYMOUS_USER) ? Constants.SYSTEM : auditBy);

    }
}
