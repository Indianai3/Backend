package com.service.controller;

import com.service.model.request.Generator;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/generator")
public interface GeneratorController {
    @PostMapping("/generate-ssd1d")
    ResponseEntity<InputStreamResource> generateSSD1D(@RequestBody Generator generator) throws IOException;

    @PostMapping("/generate-sd1")
    ResponseEntity<InputStreamResource> generateSD1(@RequestBody Generator generator) throws IOException;

    @GetMapping("/run")
    ResponseEntity<Object> run(@RequestParam Integer poolSize, @RequestParam Integer times);

}
