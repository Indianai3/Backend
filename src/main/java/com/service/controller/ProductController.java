package com.service.controller;

import com.service.model.entity.Product;
import com.service.model.request.ProductDTO;
import com.service.service.product.ProductService;
import com.service.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/product")
@CrossOrigin
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

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestHeader(value = Constants.FIREBASE_UID, required = false) String fbUid,
                                                           @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.addProduct(fbUid, productDTO));
    }

    @PutMapping("/update")
    public ResponseEntity<Product> updateProduct(@RequestHeader(value = Constants.FIREBASE_UID, required = false) String fbUid,
                                              @RequestBody Product product) {
        return ResponseEntity.ok(productService.update(fbUid, product));
    }
}
