package com.service.controller;

import com.service.model.entity.Product;
import com.service.service.ProductService;
import com.service.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/product")
public class ProductController {
    private final ProductService productService;
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProduct(@RequestHeader(value = Constants.FIREBASE_UID, required = false) String fbUid,
                                     @RequestParam String productId) {
        return ResponseEntity.ok(productService.getProduct(fbUid, productId));
    }

    @PostMapping("/list")
    public ResponseEntity<List<Product>> getListOfProducts(@RequestHeader(value = Constants.FIREBASE_UID, required = false) String fbUid,
                                                        @RequestBody List<String> productIds) {
        return ResponseEntity.ok(productService.getListOfProducts(fbUid, productIds));
    }
}
