package com.service.service.ai;

import com.service.client.ai.SegmidFeignClient;
import com.service.dao.FirebaseDao;
import com.service.model.entity.Image;
import com.service.model.request.GeneratorRequest;
import com.service.model.request.SegmidRequest;
import com.service.utils.Constants;
import com.service.utils.GenericUtils;
import com.service.service.ImageUploaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.HashMap;
import java.util.Random;


@Component
@RequiredArgsConstructor
@Slf4j
public class SegmidImageGenerator implements ImageGenerator {
    private final SegmidFeignClient segmidFeignClient;
    private final ImageUploaderService imageUploaderService;
    private final FirebaseDao firebaseDao;
    private final Random random = new Random();
    @Value("${segmidToken}")
    private String token;

    @Value("${firebase.StorageBucketUrl}")
    private String fbStorageBucketUrl;

    @Override
    public Image generateImage(String fbUid, GeneratorRequest generatorRequest) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        byte[] ImageBytes = segmidFeignClient.generateByModel(getToken(), getModel(generatorRequest),
                getSegMidRequest(generatorRequest));
        stopWatch.stop();
        log.info("Time taken for segmid request is: {}", stopWatch.getTotalTimeSeconds());

        String storagePath = String.format("%s/%s.png", Constants.AI_IMAGE_FOLDER, GenericUtils.getUuid());
        String imageUrl = "https://storage.googleapis.com/" + fbStorageBucketUrl + "/" + storagePath;

//        CompletableFuture.runAsync(() -> imageUploaderService.uploadImageToFirebaseStorageAndGetUrl(storagePath, ImageBytes));
        imageUploaderService.uploadImageToFirebaseStorageAndGetUrl(storagePath, ImageBytes);

        Image image = new Image();
        image.setImageUrl(imageUrl);
        image.setImageId(GenericUtils.getUuid());
        image.setMetaData(getMetaData(generatorRequest));
        image.setCreatedAt(System.currentTimeMillis());
        image.setFbUid(fbUid);

//        CompletableFuture.runAsync(() -> firebaseDao.saveDocument(Constants.IMAGES, image.getImageId(), image));
        firebaseDao.saveDocument(Constants.IMAGES, image.getImageId(), image);

        return image;
    }

    private HashMap<String, Object> getMetaData(GeneratorRequest generatorRequest) {
        return new HashMap<>() {{
            put("model", getModel(generatorRequest));
            put("timestamp", System.currentTimeMillis());
            put("prompt", generatorRequest.getPrompt());
            put("gender", generatorRequest.getGender());
            put("printType", generatorRequest.getPrintType());
        }};
    }

    private String getModel(GeneratorRequest generatorRequest) {
        return Constants.SEGMID_SSD1B;
    }

    private SegmidRequest getSegMidRequest(GeneratorRequest generatorRequest) {
        SegmidRequest segmidRequest = new SegmidRequest();
        segmidRequest.setPrompt(generatorRequest.getPrompt());
        segmidRequest.setSeed(random.nextLong() & Long.MAX_VALUE);
        return segmidRequest;
    }

    private String getToken() {
        // todo: implement token algo
        return token;
    }
}
