package com.service.client.auth;


import com.service.model.request.FbAuthRequest;
import com.service.model.response.FbAuthResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "FBFeignClient", url = "https://identitytoolkit.googleapis.com/v1")
@Headers("Content-Type: application/json")
public interface FBFeignClient {

    @PostMapping("/accounts:signInWithPassword?key=AIzaSyDdro-Gy2WE7Xf1-capRrKNrU0pQdA0Gkg")
    FbAuthResponse getToken(@RequestBody FbAuthRequest authRequest);
}

//curl --location --request POST 'https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=AIzaSyDdro-Gy2WE7Xf1-capRrKNrU0pQdA0Gkg' --header 'content-type: application/json' --data-raw '{
//        "email": "varshith@gmail.com",                                                                                                                                               "password": "12345678",                                                                                                                                                      "returnSecureToken": true                                                                                                                                                }'

