package com.service.service.ai;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.service.client.auth.FBFeignClient;
import com.service.model.ModelMapper;
import com.service.model.request.FbAuthRefreshRequest;
import com.service.model.request.FbAuthRequest;
import com.service.model.response.FbAuthRefreshResponse;
import com.service.model.response.FbAuthResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    @Value("${firebase.authKey}")
    private String fbAuthKey;

    private final FirebaseAuth fbAuth = FirebaseAuth.getInstance();

    private final FBFeignClient fbFeignClient;

    @SneakyThrows
    public String getUserId(String token) {
        FirebaseToken decodedToken = fbAuth.verifyIdToken(token);
        return decodedToken.getUid();
    }

    public FbAuthResponse signIn(FbAuthRequest fbAuthRequest) {
        log.info("Trying to sign-in for user with email: {}", fbAuthRequest.getEmail());
        return fbFeignClient.getToken(fbAuthKey, fbAuthRequest);
    }

    public FbAuthResponse signUp(FbAuthRequest fbAuthRequest) throws FirebaseAuthException {
        log.info("Trying to sign-up an account for user with email: {}", fbAuthRequest.getEmail());
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(fbAuthRequest.getEmail())
                .setPassword(fbAuthRequest.getPassword());
        UserRecord userRecord = fbAuth.createUser(request);
        log.info("Successfully created userRecord for email: {} with uid: {}", fbAuthRequest.getEmail(), userRecord.getUid());
        return signIn(fbAuthRequest);
    }

    public FbAuthResponse refreshToken(FbAuthRefreshRequest fbAuthRefreshRequest) {
        log.info("Trying to refresh token for refreshTokenId: {}", fbAuthRefreshRequest.getRefresh_token());
        FbAuthRefreshResponse fbAuthRefreshResponse = fbFeignClient.getTokenUsingRefreshId(fbAuthKey, fbAuthRefreshRequest);
        return ModelMapper.mapToFbAuthResponse(fbAuthRefreshResponse);
    }
}
