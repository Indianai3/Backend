package com.service.controller;

import com.service.model.request.FbAuthRequest;
import com.service.model.response.FbAuthResponse;
import com.google.firebase.auth.FirebaseAuthException;
import com.service.service.ai.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping(path = "/test")
    public String test() {
        return "Hello world";
    }

    @PostMapping("/signIn")
    public FbAuthResponse signIn(@RequestBody FbAuthRequest fbAuthRequest) {
        // todo: error handling
        return authService.signIn(fbAuthRequest);
    }

    @PostMapping("/signUp")
    public FbAuthResponse signUp(@RequestBody FbAuthRequest fbAuthRequest) throws FirebaseAuthException {
        // todo: error handling
        return authService.signUp(fbAuthRequest);
    }

    //todo: implement get id token using refresh token
//    @PostMapping("/token")
//    public String getIdTokenFromRefreshToken(@RequestBody String refreshToken) throws FirebaseAuthException {
//        // todo: error handling
//        return authService.getIdTokenFromRefreshToken(refreshToken);
//    }

    @PostMapping("/userId")
    public String getUserId(@RequestBody String token) throws FirebaseAuthException {
        return authService.getUserId(token);
    }
}