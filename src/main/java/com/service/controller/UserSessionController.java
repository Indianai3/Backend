package com.service.controller;

import com.service.model.entity.DetailedProduct;
import com.service.model.entity.UserSession;
import com.service.model.response.UserSessionDTO;
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
    public UserSessionDTO getUserSession(@RequestHeader(value = Constants.FIREBASE_UID) String fbUid) {
        return mapUserSessionToDto(userSessionService.getUserSession(fbUid));
    }

    @PostMapping("/update/session")
    public UserSessionDTO updateUserSession(@RequestHeader(value = Constants.FIREBASE_UID) String fbUid,
                                         @RequestBody UserSessionDTO userSession) {
        return mapUserSessionToDto(userSessionService.updateUserSession(fbUid, userSession));
    }

    @PostMapping("/add/favourites")
    public UserSessionDTO addToFavourties(@RequestHeader(value = Constants.FIREBASE_UID) String fbUid,
                                          @RequestBody DetailedProduct detailedProduct) {
        return mapUserSessionToDto(userSessionService.addToFavourties(fbUid, detailedProduct));
    }

    @PostMapping("/add/cart")
    public UserSessionDTO addToCart(@RequestHeader(value = Constants.FIREBASE_UID) String fbUid,
                                 @RequestBody DetailedProduct detailedProduct) {
        return mapUserSessionToDto(userSessionService.addToCart(fbUid, detailedProduct));
    }

    private UserSessionDTO mapUserSessionToDto(UserSession userSession) {
        return new UserSessionDTO(userSession);
    }

}
