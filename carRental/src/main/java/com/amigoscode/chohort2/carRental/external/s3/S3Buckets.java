package com.amigoscode.chohort2.carRental.external.s3;


import com.amigoscode.chohort2.carRental.image.ImageS3Handler;
import com.amigoscode.chohort2.carRental.image.MultiMediaS3Handler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
@ConfigurationProperties(prefix = "aws.s3.buckets")
public class S3Buckets {

    private List<MultiMediaS3Handler.S3ObjectDomain> s3ObjectDomains;

    /**
     * Organization name or base bucket name;
     * */
    private String orgName;

    /**
     * Account name that builds with orgName bucket name;
     * */

    private String account;

    /**
     * Suffix representing the bucket storing original files.
    * */

    private String originalSuffix;

    /**
     * Creating a bean seems to be kind of hardcoding, this allows to resolve the domain while bean processing, though,
     * and might be more effective than more generic "resolving" the interface on every single api call. Nonetheless,
     * interfaces {@link com.amigoscode.chohort2.carRental.image.MultiMediaS3Handler} and {@link com.amigoscode.chohort2.carRental.image.ImageS3Handler}
     * provide the ability and its default implementation of resolving bucket full name and bucket prefix inflight.
     * In case, there are only a few buckets and prefixes (business domains) as well as only a decent amount of dependent calls
     * is expected, automatic resolving appears to be a better solution.
     *
     * @see ImageS3Handler#getImagesDomain()
     * @see ImageS3Handler#getFullBucketName()
     * @see ImageS3Handler#getFullObjectName(String, Object, String)
     */

    @Bean
    @Qualifier(value = "carsS3Domain")
    MultiMediaS3Handler.S3ObjectDomain getCarsS3Domain() {
        return s3ObjectDomains
                .stream()
                .filter(domain -> domain.getName().equals("cars"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("domain %s doesn't exist".formatted("cars")));
    }

    /**
     * @see S3Buckets#getCarsS3Domain()
     */
    private final String bucketFullName = MultiMediaS3Handler.resolveBucketNameWithHyphens(
            getOrgName(),
            getAccount(),
            getOriginalSuffix());

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOriginalSuffix() {
        return originalSuffix;
    }

    public void setOriginalSuffix(String originalSuffix) {
        this.originalSuffix = originalSuffix;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public List<MultiMediaS3Handler.S3ObjectDomain> getS3domains() {
        return s3ObjectDomains;
    }

    public void setS3domains(List<MultiMediaS3Handler.S3ObjectDomain> s3ObjectDomains) {
        this.s3ObjectDomains = s3ObjectDomains;
    }

    public String getBucketFullName() {
        return bucketFullName;
    }
}
