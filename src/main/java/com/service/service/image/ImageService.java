package com.service.service.image;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import com.service.dao.FirebaseDao;
import com.service.model.entity.Image;
import com.service.utils.Constants;
import com.service.utils.GenericUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class ImageService {

    private final FirebaseDao firebaseDao;
    public Image saveImage(String fbUid, String imageUrl, HashMap<String, Object> metaData) {
        Image image = new Image();
        image.setImageUrl(imageUrl);
        image.setImageId(GenericUtils.getUuid());
        image.setMetaData(metaData);
        image.setCreatedAt(System.currentTimeMillis());
        image.setFbUid(fbUid);

//        CompletableFuture.runAsync(() -> firebaseDao.saveDocument(Constants.IMAGES, image.getImageId(), image));
        firebaseDao.saveDocument(Constants.IMAGES, image.getImageId(), image);
        return image;
    }

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

    public Image getImage(String fbUid, String imageId) {
        return firebaseDao.getDocument(Constants.IMAGES, imageId, Image.class);
    }

    public Image createImage(String fbUid, Image image) {
        if(Strings.isBlank(image.getImageId())) {
            image.setImageId(GenericUtils.getUuid());
        }
        image.setCreatedAt(System.currentTimeMillis());
        firebaseDao.saveDocument(Constants.IMAGES, image.getImageId(), image);
        return image;
    }

    private String getPublicUrl(BlobInfo blob, String token) {
        return "https://firebasestorage.googleapis.com/v0/b/" + blob.getBucket() + "/o/" + URLEncoder.encode(blob.getName(), StandardCharsets.UTF_8) + "?alt=media&token=" + token;
    }
}
