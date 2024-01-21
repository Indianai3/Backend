package com.service.service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import com.service.utils.exception.BackendException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class ImageUploaderService {

    @SneakyThrows
    public String uploadImageToFirebaseStorageAndGetUrl(String storagePath, byte[] imageBytes) {
        Bucket bucket = StorageClient.getInstance().bucket();
        try (InputStream ignored = new ByteArrayInputStream(imageBytes)) {
            BlobId blobId = BlobId.of(bucket.getName(), storagePath);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType("image/jpeg")
                    .build();

            // Upload the image to Firebase Storage
            bucket.getStorage().create(blobInfo, imageBytes);

            return getPublicUrl(blobInfo);
        } catch (Exception e) {
            throw new BackendException(e);
        }
    }

    private String getPublicUrl(BlobInfo blob) {
        return "https://storage.googleapis.com/" + blob.getBucket() + "/" + blob.getName();
    }

}
