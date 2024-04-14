package com.service.service.session;

import com.service.dao.FirebaseDao;
import com.service.exception.ResourceNotFoundException;
import com.service.model.entity.DetailedProduct;
import com.service.model.entity.Product;
import com.service.model.entity.UserSession;
import com.service.service.product.ProductService;
import com.service.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserSessionService {

    private final FirebaseDao firebaseDao;
    private final ProductService productService;

    public UserSession getUserSession(String fbUid) {
        return firebaseDao.getDocument(Constants.USERS, fbUid, UserSession.class);
    }

    public UserSession updateUserSession(String fbUid, UserSession userSession) {
        firebaseDao.saveDocument(Constants.USERS, fbUid, userSession);
        return userSession;
    }

    public UserSession addToFavourties(String fbUid, DetailedProduct detailedProduct) {
        if(Objects.isNull(detailedProduct.getProduct())) {
            enhahnceDetailedProduct(detailedProduct);
        }
        UserSession userSession = new UserSession();
        try {
            userSession = getUserSession(fbUid);
        } catch (ResourceNotFoundException ignored) {

        }
        String key = getKey(fbUid, detailedProduct);
        userSession.getFavourites().put(key, detailedProduct);
        firebaseDao.saveDocument(Constants.USERS, fbUid, userSession);
        return userSession;
    }

    private void enhahnceDetailedProduct(DetailedProduct detailedProduct) {
        try {
            Product product = productService.getProduct(null, detailedProduct.getProductId());
            detailedProduct.setProduct(product);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(String.format("No Product with id: %s exists", detailedProduct.getProductId()));
        }
    }

    public UserSession addToCart(String fbUid, DetailedProduct detailedProduct) {
        if(Objects.isNull(detailedProduct.getProduct())) {
            enhahnceDetailedProduct(detailedProduct);
        }
        UserSession userSession = new UserSession();
        try {
            userSession = getUserSession(fbUid);
        } catch (ResourceNotFoundException ignored) {

        }
        String key = getKey(fbUid, detailedProduct);
        userSession.getCartEntries().put(key, detailedProduct);
        firebaseDao.saveDocument(Constants.USERS, fbUid, userSession);
        return userSession;
    }

    private String getKey(String fbUid, DetailedProduct detailedProduct) {
        return String.format("%s__%s__%s", fbUid, detailedProduct.getProductId(),
                detailedProduct.getProductMeta().getSize().name());
    }
}
