package com.service.controller;

import com.service.client.auth.FBFeignClient;
import com.service.model.request.FbAuthRequest;
import com.service.model.response.FbAuthResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AppController {

    private final FBFeignClient fbFeignClient;

    @GetMapping(path = "/test")
    public String test(Principal principal) {
        return principal.getName();
    }

    @PostMapping("/authenticate")
    public FbAuthResponse authenticate(@RequestBody FbAuthRequest fbAuthRequest) {
        return fbFeignClient.getToken(fbAuthRequest);
    }

    @PostMapping("/userId")
    public String getUserId(@RequestBody String token) throws FirebaseAuthException {
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
        return decodedToken.getUid();
    }
}