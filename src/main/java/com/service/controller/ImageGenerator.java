package com.service.controller;

import com.service.model.request.Generator;
import com.service.model.response.GeneratedResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/generator")
public interface ImageGenerator {
    @PostMapping("/generate")
    ResponseEntity<GeneratedResponse> generate(@RequestHeader(value = "Authorization", required = false) String authHeader,
                                               @RequestBody Generator generator) throws IOException;

    @GetMapping("/get-generatedImage")
    ResponseEntity<InputStreamResource> generate(@RequestHeader(value = "Authorization", required = false) String authHeader,
                                                 @RequestParam String imageId) throws IOException;

    @PostMapping("/generate-and-get")
    ResponseEntity<InputStreamResource> generateAndGet(@RequestHeader(value = "Authorization", required = false) String authHeader,
                                               @RequestBody Generator generator) throws IOException;

}
