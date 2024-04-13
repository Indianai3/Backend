package com.service.service.segmid;

import com.google.cloud.firestore.DocumentReference;
import com.service.client.ai.SegmidFeignClient;
import com.service.exception.TooManyRequestsException;
import com.service.model.entity.SegmidToken;
import com.service.model.request.SegmidRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SegmidService {
    private final SegmidTokenManager segmidTokenManager;
    private final SegmidFeignClient segmidFeignClient;

    @Value("${segmidAuth.retry:5}")
    private Integer retryCount;

    public byte[] generateByModel(String authorization, String model, SegmidRequest segmidRequest) {
        return segmidFeignClient.generateByModel(authorization, model, segmidRequest);
    }

    @SneakyThrows
    public byte[] generateByModel(String model, SegmidRequest segmidRequest) {
        for(int times = 0; times < retryCount; times ++) {
            Pair<DocumentReference, SegmidToken> token = segmidTokenManager.getToken();
            try {
                return generateByModel(token.getRight().getToken(), model, segmidRequest);
            } catch (Exception e) {
                if(e.getMessage().contains("Unathorised") || e.getMessage().contains("401")) {
                    segmidTokenManager.removeTokenFromStore(token);
                } else {
                    throw new Exception(e.getMessage());
                }
            }
        }

        throw new TooManyRequestsException("Unable to process request due to heavy load");
    }
}
