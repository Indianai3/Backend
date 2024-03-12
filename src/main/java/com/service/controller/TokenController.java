package com.service.controller;

import com.google.cloud.firestore.DocumentReference;
import com.service.model.entity.SegmidToken;
import com.service.service.segmid.SegmidTokenManager;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/token")
@CrossOrigin
public class TokenController {
    private final SegmidTokenManager segmidTokenManager;
    @PostMapping("/add-tokens")
    public Boolean addTokens(@RequestBody List<String> tokens) {
        return segmidTokenManager.addTokens(tokens);
    }

    @GetMapping("/refresh")
    public Boolean refreshCache() {
        return segmidTokenManager.refreshCache();
    }

    @GetMapping("/list")
    public List<Pair<DocumentReference, SegmidToken>> listTokens() {
        return segmidTokenManager.getAll();
    }
}
