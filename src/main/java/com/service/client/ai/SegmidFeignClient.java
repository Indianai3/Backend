package com.service.client.ai;


import com.service.model.request.SegmidRequest;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "SegmidFeignClient", url = "${segmid.url}")
@Headers("Content-Type: application/json")
public interface SegmidFeignClient {

    @PostMapping("/v1/{model}")
    byte[] generateByModel(@RequestHeader(value = "Authorization") String authorization,
                         @PathVariable(value = "model") String model,
                         @RequestBody SegmidRequest segmidRequest);

}
