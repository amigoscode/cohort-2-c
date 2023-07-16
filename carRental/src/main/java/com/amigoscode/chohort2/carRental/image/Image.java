package com.amigoscode.chohort2.carRental.image;

import org.springframework.web.multipart.MultipartFile;

public interface Image <ID> {
    void uploadImage(ID domanId, MultipartFile file);
    byte[] getOriginalUresizedImage (ID domainId);

    byte [] getOriginalResizedImage(ID domainId);

}

