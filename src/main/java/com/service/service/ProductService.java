package com.service.service;

import com.service.dao.FirebaseDao;
import com.service.model.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.service.utils.Constants.PRODUCTS;


@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final FirebaseDao firebaseDao;

    public Product getProduct(String fbUid, String productId) {
        log.info("Request product received for ProductId {}", productId);
        Product product = firebaseDao.getDocument(PRODUCTS, productId, Product.class);
        log.info("Successfully found product for ProductId {}", productId);
        return product;
    }

    public List<Product> getListOfProducts(String fbUid, List<String> productIds) {
        return productIds.stream()
                .map(productId -> getProduct(fbUid, productId))
                .collect(Collectors.toList());
    }
}
