package com.amigoscode.chohort2.carRental.image;

import org.springframework.web.multipart.MultipartFile;

public interface MultiMediaS3<T, ID> {
    default  T uploadImage(ID domainId,MultipartFile file) {
        return null;
    }

    default byte[] getOriginalImage(ID domainId){
        return null;
    }

    default byte[] getResizedImage(ID domainId){
        return null;
    }

    default String getS3FileDomainName() {
        return null;
    }

    default String getImageUrlOrThrow(T domain){
        return null;
    }

}
