package com.service.service.ai;

import com.service.utils.exception.BackendException;
import com.service.model.ImageGeneratorType;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Objects;

@Component
public class ImageGeneratorProvider {
    private final HashMap<ImageGeneratorType, ImageGenerator> typeToImageGeneratorMap = new HashMap<>();

    @Autowired
    public ImageGeneratorProvider(ApplicationContext applicationContext) {
        typeToImageGeneratorMap.put(ImageGeneratorType.SEGMID,
                applicationContext.getBean(SegmidImageGenerator.class));
    }

    @SneakyThrows
    public ImageGenerator provideByType(ImageGeneratorType type) {
        if(Objects.isNull(type)) {
            // default model
            return typeToImageGeneratorMap.get(ImageGeneratorType.SEGMID);
        }
        else if (!typeToImageGeneratorMap.containsKey(type)) {
            throw new BackendException(String.format("ImageGeneratorType: %s not found", type.name()), 404);
        }
        return typeToImageGeneratorMap.get(type);
    }
}
