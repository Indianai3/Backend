//package com.service.controller;
//
//import com.service.model.entity.UserSession;
//import com.service.model.response.UserSessionDTO;
//import com.service.service.session.UserSessionService;
//import com.service.utils.Constants;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping(value = "/order")
//@CrossOrigin
//public class OrderController {
//    private final UserSessionService userSessionService;
//
//    @GetMapping("/session")
//    public UserSession getUserSession(@RequestHeader(value = Constants.FIREBASE_UID) String fbUid) {
//        return userSessionService.getUserSession(fbUid);
//    }
//
//    @PostMapping("/update/session")
//    public UserSessionDTO updateUserSession(@RequestHeader(value = Constants.FIREBASE_UID) String fbUid,
//                                         @RequestBody UserSessionDTO userSession) {
//        return userSessionService.updateUserSession(fbUid, userSession);
//    }
//
//    public Order checkoutOrder(@RequestHeader(value = Constants.FIREBASE_UID) String fbUid,
//                               @RequestBody Order order) {
//
//        return null;
//    }
//}