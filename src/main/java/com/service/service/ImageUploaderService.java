package com.service.service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import com.service.utils.GenericUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class ImageUploaderService {

    @SneakyThrows
    public String uploadImageToFirebaseStorageAndGetUrl(String storagePath, byte[] imageBytes) {
        Bucket bucket = StorageClient.getInstance().bucket();
        try (InputStream ignored = new ByteArrayInputStream(imageBytes)) {
            BlobId blobId = BlobId.of(bucket.getName(), storagePath);
            String token = GenericUtils.getUuid();
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType("image/jpeg")
                    .setMetadata(new HashMap<>(){{put("firebaseStorageDownloadTokens", token);}})
                    .build();

            // Upload the image to Firebase Storage
            bucket.getStorage().create(blobInfo, imageBytes);

            return getPublicUrl(blobInfo, token);
        } catch (Exception e) {
            throw new Exception(String.format("Error while persisting image: %s", e.getMessage()));
        }
    }

    private String getPublicUrl(BlobInfo blob, String token) {
        return "https://firebasestorage.googleapis.com/v0/b/" + blob.getBucket() + "/o/" + URLEncoder.encode(blob.getName(), StandardCharsets.UTF_8) + "?alt=media&token=" + token;
    }

}
