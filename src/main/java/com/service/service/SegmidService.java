package com.service.service;

import com.service.client.ai.SegmidFeignClient;
import com.service.model.entity.GeneratedImage;
import com.service.model.request.Generator;
import com.service.model.request.SegmidRequest;
import com.service.model.response.SegmidImage;
import com.service.utils.GenericUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SegmidService {

    @Value("${segmidToken}")
    private String token;

    private final SegmidFeignClient segmidFeignClient;
    private final Random random = new Random();

    public GeneratedImage generateBase64Image(Generator generator) {
        SegmidRequest segmidRequest = new SegmidRequest();
        segmidRequest.setPrompt(generator.getPrompt());
        segmidRequest.setSeed(random.nextLong() & Long.MAX_VALUE);
        byte[] imageBytes = segmidFeignClient.generateSSD1B(token, segmidRequest);
        return new GeneratedImage(GenericUtils.getUuid(),
                GenericUtils.encodeByteArrayToBase64(imageBytes),
                getSSD1DMetaData(), System.currentTimeMillis());
    }

    public SegmidImage getGeneratedImage(Generator generator) {
        SegmidRequest segmidRequest = new SegmidRequest();
        segmidRequest.setPrompt(generator.getPrompt());
        segmidRequest.setSeed(random.nextLong() & Long.MAX_VALUE);
        byte[] imageBytes = segmidFeignClient.generateSSD1B(token, segmidRequest);
        return new SegmidImage(GenericUtils.getUuid(),
                imageBytes, getSSD1DMetaData());
    }

    private static HashMap<String, Object> getSSD1DMetaData() {
        return new HashMap<>() {{
            put("model", "ssd-1b");
        }};
    }
}
