package com.service.controller;

import com.service.client.auth.FBFeignClient;
import com.service.model.request.FbAuthRequest;
import com.service.model.response.FbAuthResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    @Value("${firebase.authKey}")
    private String authKey;

    private final FBFeignClient fbFeignClient;

    @GetMapping(path = "/test")
    public String test() {
        return "Hello world";
    }

    @PostMapping("/authenticate")
    public FbAuthResponse authenticate(@RequestBody FbAuthRequest fbAuthRequest) {
        // todo: error handling
        return fbFeignClient.getToken(authKey, fbAuthRequest);
    }

    @PostMapping("/userId")
    public String getUserId(@RequestBody String token) throws FirebaseAuthException {
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
        return decodedToken.getUid();
    }
}