package com.service.service;

import com.service.controller.ImageGenerator;
import com.service.dao.FirebaseDao;
import com.service.model.entity.ArchiveEvent;
import com.service.model.entity.GeneratedImage;
import com.service.model.entity.User;
import com.service.model.request.Generator;
import com.service.model.response.GeneratedResponse;
import com.service.queue.ArchiveEventQueue;
import com.service.utils.AuthUtils;
import com.service.model.Collections;
import com.service.utils.GenericUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageGeneratorService implements ImageGenerator {

    private final GeneratorService generatorService;
    private final FirebaseDao firebaseDao;
    @Override
    public ResponseEntity<GeneratedResponse> generate(String authHeader, Generator generator) {
        User user = AuthUtils.getUserFromAuthHeader(authHeader);
        GeneratedResponse generatedResponse = generatorService.generateSSD1DV0(generator);
        publishArchiveEvent(generator, generatedResponse, user);
        return ResponseEntity.ok(generatedResponse);
    }

    @Override
    public ResponseEntity<InputStreamResource> generate(String authHeader, String imageId) throws IOException {
        GeneratedImage document = firebaseDao.getDocument(Collections.IMAGES, imageId, GeneratedImage.class);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(GenericUtils.convertBase64ToInputStreamResource(document.getBase64Image()));
    }

    @Override
    public ResponseEntity<InputStreamResource> generateAndGet(String authHeader, Generator generator) throws IOException {
        User user = AuthUtils.getUserFromAuthHeader(authHeader);
        GeneratedResponse generatedResponse = generatorService.generateSSD1DV0(generator);
        publishArchiveEvent(generator, generatedResponse, user);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(generatedResponse.getImage().getImageStream());
    }

    private void publishArchiveEvent(Generator generator, GeneratedResponse generatedResponse, User user) {
        ArchiveEvent archiveEvent = new ArchiveEvent();
        archiveEvent.setDocId(GenericUtils.getUuid());
        archiveEvent.setImageId(generatedResponse.getImage().getDocId());
        archiveEvent.setGenerationMetaData(generatedResponse.getImage().getMetaData());
        archiveEvent.setRequestedUserUid(user.getFirebaseUid());
        archiveEvent.setCreatedAt(System.currentTimeMillis());
        archiveEvent.setRequest(generator);
        ArchiveEventQueue.getInstance().publish(archiveEvent);
    }

}
