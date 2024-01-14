package com.service.client.ai;


import com.service.model.request.SegmidRequest;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "SegmidFeignClient", url = "https://api.segmind.com")
@Headers("Content-Type: application/json")
public interface SegmidFeignClient {

    @PostMapping("/v1/ssd-1b")
    byte[] generateSSD1B(@RequestHeader(value = "Authorization") String authorization,
                         @RequestBody SegmidRequest segmidRequest);

    @PostMapping("/v1/sd1.5-realisticvision")
    byte[] generateSD1(@RequestHeader(value = "Authorization") String authorization,
                         @RequestBody SegmidRequest segmidRequest);
}
