package com.service.service.ai;

import com.service.dao.FirebaseDao;
import com.service.model.ImageGeneratorType;
import com.service.model.ProductType;
import com.service.model.entity.Image;
import com.service.model.entity.Product;
import com.service.model.request.GeneratorRequest;
import com.service.utils.Constants;
import com.service.utils.GenericUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiService {
    private final ImageGeneratorProvider imageGeneratorProvider;
    private final FirebaseDao firebaseDao;

    public Product generateAndGet(String fbUid, GeneratorRequest generatorRequest) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("Received request to generate Ai Image for fbUid: {}, generatorRequest: {}", fbUid, generatorRequest);
        Image image = imageGeneratorProvider.provideByType(ImageGeneratorType.SEGMID)
                .generateImage(fbUid, generatorRequest);

        Product product = getProduct(fbUid, generatorRequest, image);

        firebaseDao.saveDocument(Constants.PRODUCTS, product.getProductId(), product);

        stopWatch.stop();
        log.info("Time taken for generateAndGet is {}", stopWatch.lastTaskInfo().getTimeSeconds());
        return product;
    }

    private Product getProduct(String fbUid, GeneratorRequest generatorRequest, Image image) {
        Product product = new Product();
        product.setImages(Collections.singletonList(image));
        product.setProductId(GenericUtils.getUuid());
        product.setCategory(generatorRequest.getCategory());
        product.setGender(generatorRequest.getGender());
        product.setPrice(getPrice());
        product.setProductType(ProductType.AI);
        product.setTitle(generatorRequest.getPrompt());
        product.setPrompt(generatorRequest.getPrompt());
        product.setDescription(generatorRequest.getPrompt());
        product.setImage(image);
        product.setFbUid(fbUid);
        return product;
    }

    private Integer getPrice() {
        // todo: plan to set a number for this
        return 999;
    }
}
