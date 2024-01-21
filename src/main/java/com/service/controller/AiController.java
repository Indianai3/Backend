package com.service.controller;

import com.service.model.entity.Product;
import com.service.model.request.GeneratorRequest;
import com.service.service.ai.AiService;
import com.service.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/ai")
public class AiController {
    private final AiService aiService;

    @PostMapping("/generate")
    public ResponseEntity<Product> generateAndGet(@RequestHeader(value = Constants.FIREBASE_UID, required = false) String fbUid,
                                                  @RequestBody GeneratorRequest generatorRequest) {
        return ResponseEntity.ok(aiService.generateAndGet(fbUid, generatorRequest));
    }
}
