package com.amigoscode.chohort2.carRental.image;

import com.amigoscode.chohort2.carRental.car.Car;
import com.amigoscode.chohort2.carRental.constants.ErrorConstants;
import com.amigoscode.chohort2.carRental.exception.ApiRequestException;
import com.amigoscode.chohort2.carRental.validation.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public abstract class ImageImpl<T, ID> extends SimpleJpaRepository<T, ID> implements Image<ID> {
    @Override
    @Transactional
    public void uploadImage(ID domanId, MultipartFile file) {
        T car = getCarIfBelongsToCurrentProviderOrThrow(carId);
        String carImageId = UUID.randomUUID().toString();
        try {
            s3Service.putObject(
                    s3Buckets.getCar(),
                    "images/%s/%s".formatted(carId, carImageId),
                    file.getBytes()
            );
        } catch (IOException e) {

            //TODO: change exception
            throw new RuntimeException("failed to upload profile image", e);
        }
        car.setImgUrl(carImageId);
        save(car);
    }



    @Override
    public byte[] getOriginalUresizedImage(ID domainId) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new ApiRequestException(ErrorConstants.CAR_NOT_FOUND));
        byte[] unresizedImage = getCarImage(carId, car);
        return new byte[0];
    }

    @Override
    public byte[] getOriginalResizedImage(ID domainId) {
        Car car = getCarIfBelongsToCurrentProviderOrThrow(carId);
        return resizeImage(getCarImage(carId, car));
    }

    private byte[] resizeImage(byte[] originalImage, int targetWidth, int targetHeight) throws IOException {
        BufferedImage bufferedOriginalImage = ImageIO.read(new ByteArrayInputStream(originalImage));
        BufferedImage bufferedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.drawImage(bufferedOriginalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpeg", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    byte[] getCarImage(ID carId, T car) {

        if (StringUtils.isBlank(car.getImgUrl())) {
            throw new ApiRequestException(ErrorConstants.IMAGE_NOT_FOUND, "no image exists" );
        }

        byte[] carImage = s3Service.getObject(
                s3Buckets.getCar(),
                "images/%s/%s".formatted(carId, car.getImgUrl())
        );
        return carImage;
    }

    public T getCarIfBelongsToCurrentProviderOrThrow(ID id){
        T car = findById(id).orElseThrow(() -> new ApiRequestException(ErrorConstants.CAR_NOT_FOUND));

        Validator.invalidateIfFalse(() -> car.getCarProviderId().equals(getCurrentCarProviderId()),
                ErrorConstants.CAR_PROVIDER_USER,
                "car doesn't belong to the provider");

        return car;
    }
}
