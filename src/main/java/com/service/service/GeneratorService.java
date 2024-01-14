package com.service.service;

import com.service.client.ai.SegmidFeignClient;
import com.service.controller.GeneratorController;
import com.service.dao.FirebaseDao;
import com.service.model.entity.GeneratedImage;
import com.service.model.request.Generator;
import com.service.model.request.SegmidRequest;
import com.service.model.response.GeneratedResponse;
import com.service.model.response.SegmidImage;
import com.service.model.Collections;
import com.service.utils.GenericUtils;
import com.service.utils.MapperHelper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
public class GeneratorService implements GeneratorController {

    @Value("${segmidToken}")
    private String token;

    private final SegmidFeignClient segmidFeignClient;
    private final FirebaseDao firebaseDao;
    private final SegmidService segmidService;
    private final Random random = new Random();

    @Override
    public ResponseEntity<InputStreamResource> generateSSD1D(Generator generator) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        System.out.println("called generated, by thread: " + Thread.currentThread().getId());
        SegmidRequest segmidRequest = new SegmidRequest();
        segmidRequest.setPrompt(generator.getPrompt());
        segmidRequest.setSeed(random.nextLong() & Long.MAX_VALUE);
        byte[] imageBytes = segmidFeignClient.generateSSD1B(token, segmidRequest);
        if(Objects.nonNull(generator.getHeight()) && Objects.nonNull(generator.getWidth())) {
            imageBytes = convertImage(imageBytes, generator.getWidth(), generator.getHeight());
        }
        InputStreamResource inputStreamResource = new InputStreamResource(new ByteArrayInputStream(imageBytes));
        stopWatch.stop();
        System.out.println("called generated, by thread: " + Thread.currentThread().getId() + " ended, time taken = " + stopWatch.getTotalTimeSeconds());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(inputStreamResource);
    }

    public GeneratedResponse generateSSD1DV0(Generator generator) {
        SegmidImage segmidImage = segmidService.getGeneratedImage(generator);

        String ImageId = saveImage(Collections.IMAGES, new GeneratedImage(segmidImage.getDocId(),
                GenericUtils.encodeByteArrayToBase64(segmidImage.getImageBytes()),
                segmidImage.getMetaData(),
                System.currentTimeMillis()));
        return new GeneratedResponse(MapperHelper.mapSegmidImageToResponse(segmidImage),
                generator,
                System.currentTimeMillis());
    }

    private String saveImage(String imageCollection, GeneratedImage generatedImage) {
        return firebaseDao.saveDocument(imageCollection, generatedImage.getDocId(), generatedImage);
    }

    @Override
    public ResponseEntity<InputStreamResource> generateSD1(Generator generator) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        System.out.println("called generated, by thread: " + Thread.currentThread().getId());
        SegmidRequest segmidRequest = new SegmidRequest();
        segmidRequest.setPrompt(generator.getPrompt());
        segmidRequest.setImg_width(768);
        segmidRequest.setImg_height(1024);
        segmidRequest.setSeed(random.nextLong() & Long.MAX_VALUE);
        byte[] imageBytes = segmidFeignClient.generateSD1(token, segmidRequest);
        InputStreamResource inputStreamResource = new InputStreamResource(new ByteArrayInputStream(imageBytes));
        stopWatch.stop();
        System.out.println("called generated, by thread: " + Thread.currentThread().getId() + " ended, time taken = " + stopWatch.getTotalTimeSeconds());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(inputStreamResource);
    }

    @Override
    public ResponseEntity<Object> run(Integer poolSize, Integer times) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);

        Runnable task = () -> {
            generateSSD1D(new Generator("Lion in jungle, during a sunset", null, null));
        };

        for (int i = 0; i < times; i++) {
            executorService.submit(task);
        }
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(500, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException ie) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
        stopWatch.stop();
        System.out.println("Time taken = " + stopWatch.getTotalTimeSeconds());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @SneakyThrows
    public static byte[] convertImage(byte[] originalImageBytes, Integer width, Integer height) {
        InputStream inputStream = new ByteArrayInputStream(originalImageBytes);
        BufferedImage originalImage = ImageIO.read(inputStream);

        BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "jpeg", outputStream);
        byte[] convertedImageBytes = outputStream.toByteArray();

        return convertedImageBytes;
    }
}
