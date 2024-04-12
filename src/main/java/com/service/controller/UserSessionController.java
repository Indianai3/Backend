package com.service.controller;

import com.service.model.entity.DetailedProduct;
import com.service.model.entity.UserSession;
import com.service.service.session.UserSessionService;
import com.service.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/session")
@CrossOrigin
public class UserSessionController {
    private final UserSessionService userSessionService;
    @GetMapping("/session")
    public UserSession getUserSession(@RequestHeader(value = Constants.FIREBASE_UID) String fbUid) {
        return userSessionService.getUserSession(fbUid);
    }

    @PostMapping("/update/session")
    public UserSession updateUserSession(@RequestHeader(value = Constants.FIREBASE_UID) String fbUid,
                                         @RequestBody UserSession userSession) {
        return userSessionService.updateUserSession(fbUid, userSession);
    }

    @PostMapping("/add/favourites")
    public UserSession addToFavourties(@RequestHeader(value = Constants.FIREBASE_UID) String fbUid,
                                      @RequestBody DetailedProduct detailedProduct) {
        return userSessionService.addToFavourties(fbUid, detailedProduct);
    }

    @PostMapping("/add/cart")
    public UserSession addToCart(@RequestHeader(value = Constants.FIREBASE_UID) String fbUid,
                                 @RequestBody DetailedProduct detailedProduct) {
        return userSessionService.addToCart(fbUid, detailedProduct);
    }

}
