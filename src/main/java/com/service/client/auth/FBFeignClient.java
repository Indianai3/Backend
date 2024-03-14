package com.service.client.auth;


import com.service.model.request.FbAuthRefreshRequest;
import com.service.model.request.FbAuthRequest;
import com.service.model.response.FbAuthRefreshResponse;
import com.service.model.response.FbAuthResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "FBFeignClient", url = "${firebase.authUrl}")
@Headers("Content-Type: application/json")
public interface FBFeignClient {

    @PostMapping("/accounts:signInWithPassword?key={authKey}")
    FbAuthResponse getToken(@PathVariable(value = "authKey") String authKey,
                            @RequestBody FbAuthRequest authRequest);

    @PostMapping("/token?key={authKey}")
    FbAuthRefreshResponse getTokenUsingRefreshId(@PathVariable(value = "authKey") String authKey,
                                                 @RequestBody FbAuthRefreshRequest fbAuthRefreshRequest);
}

