package com.amigoscode.chohort2.carRental.image;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface ImageS3Handler<T,ID> extends MultiMediaS3Handler<ID> {

    static String MEDIA_TYPE = "images";
    default byte[] resizeFile(byte[] originalImage, int magnitude) throws IOException {

        BufferedImage bufferedOriginalImage = ImageIO.read(new ByteArrayInputStream(originalImage));
        int targetHeight = bufferedOriginalImage.getHeight() / magnitude;
        int targetWidth = bufferedOriginalImage.getWidth() / magnitude;
        BufferedImage bufferedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.drawImage(bufferedOriginalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpeg", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Transactional
    default String uploadImage(ID domainId, String S3DomainName, MultipartFile file) {
        return uploadFile(domainId, S3DomainName, file);
    }

    @Override
    default String getMEDIA_TYPE() {
        return this.MEDIA_TYPE;
    }

    default byte[] getOriginalImage(ID domainId, String S3DomainName, String fileUrl) {

        return getOriginalUnresizedFile(domainId, S3DomainName, fileUrl);
    }

    default byte[] getResizedImage(ID domainId, String S3DomainName, String fileUrl) {
        return getResizedFile(domainId, S3DomainName, fileUrl);
    }

    T uploadImage(ID domainId,MultipartFile file);

    byte[] getOriginalImage(ID domainId);
    byte[] getResizedImage(ID domainId);

    String getS3FileDomainName();

    String getImageUrlOrThrow(T domain);


}
