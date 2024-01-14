package com.service.utils;

import lombok.experimental.UtilityClass;
import org.springframework.core.io.InputStreamResource;

import javax.imageio.stream.ImageInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@UtilityClass
public class GenericUtils {
    public String encodeByteArrayToBase64(byte[] byteArray) {
        return Base64.getEncoder().encodeToString(byteArray);
    }

    public String getUuid() {
        return UUID.randomUUID().toString();
    }

    public InputStreamResource getImageStreamFromBytes(byte[] imageBytes) {
        return new InputStreamResource(new ByteArrayInputStream(imageBytes));
    }

    public InputStreamResource convertBase64ToInputStreamResource(String base64String) throws IOException {
        // Decode Base64 string to byte array
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);

        // Create an InputStream from the byte array
        ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedBytes);

        // Wrap the InputStream in an InputStreamResource
        return new InputStreamResource(inputStream);
    }
}
