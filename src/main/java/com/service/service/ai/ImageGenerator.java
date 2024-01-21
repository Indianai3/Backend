package com.service.service.ai;

import com.service.model.entity.Image;
import com.service.model.request.GeneratorRequest;

public interface ImageGenerator {
    Image generateImage(String fbUid, GeneratorRequest generatorRequest);
}
