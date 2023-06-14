package com.amigoscode.chohort2.carRental.config.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "springAuditorAware")
public class AuditConfig {

    @Bean
    AuditorAware<String> springAuditorAware(){
        return new SystemAudit();
    }
}
