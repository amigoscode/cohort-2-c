package com.amigoscode.chohort2.carRental.external.s3;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
//@AllArgsConstructor(onConstructor_ = {@Autowired})
public class S3Buckets {

    @Value("${aws.s3.buckets.domain}")
    private String domain;

    public String getDomain() {
        return domain;
    }
    public void setDomain(String domain) {
        this.domain = domain;
    }
}
