package com.service.controller;

import com.service.model.entity.Image;
import com.service.service.image.ImageService;
import com.service.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/image")
@CrossOrigin
public class ImageController {
    private final ImageService imageService;

    @GetMapping("/{imageId}")
    public Image getImage(@RequestHeader(value = Constants.FIREBASE_UID, required = false) String fbUid,
                                         @RequestParam String imageId) {
        return imageService.getImage(fbUid, imageId);
    }

    @PostMapping("/add")
    public Image addImage(@RequestHeader(value = Constants.FIREBASE_UID, required = false) String fbUid,
                          @RequestBody Image image) {
        return imageService.createImage(fbUid, image);
    }
}
