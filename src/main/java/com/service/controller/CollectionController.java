package com.service.controller;

import com.service.model.entity.Collection;
import com.service.model.response.CollectionDTO;
import com.service.service.product.CollectionService;
import com.service.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{collectionId}")
    public Collection getCollectionById(@RequestHeader(value = Constants.FIREBASE_UID, required = false) String fbUid,
                                        @RequestParam String collectionId) {
        return collectionService.getCollectionById(collectionId);
    }



}
