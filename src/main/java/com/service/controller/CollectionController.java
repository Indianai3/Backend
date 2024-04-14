package com.service.controller;

import com.service.model.entity.Collection;
import com.service.model.request.CollectionDTO;
import com.service.service.product.CollectionService;
import com.service.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/collection")
@CrossOrigin
public class CollectionController {
    private final CollectionService collectionService;

    @GetMapping("/active")
    public List<CollectionDTO> getActiveCollections(@RequestHeader(value = Constants.FIREBASE_UID, required = false) String fbUid) {
        return collectionService.getActiveCollections();
    }

    @PostMapping("/add")
    public CollectionDTO createCollection(@RequestHeader(value = Constants.FIREBASE_UID, required = false) String fbUid,
                                             @RequestBody Collection collection) {
        return collectionService.addCollection(fbUid, collection);
    }

    @GetMapping("/refresh")
    public boolean refreshCollections(@RequestHeader(value = Constants.FIREBASE_UID, required = false) String fbUid) {
        collectionService.refreshCollections();
        return true;
    }

    @SneakyThrows
    @PostMapping("/upload")
    public ResponseEntity<byte[]> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a file".getBytes());
        }

        try {
            byte[] fileBytes = file.getBytes();
            // Process the file bytes as needed (e.g., save to disk, send over network, etc.)
            return ResponseEntity.ok(fileBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reading file".getBytes());
        }
    }



}
