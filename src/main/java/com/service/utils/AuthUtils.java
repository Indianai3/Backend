package com.service.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.service.model.Role;
import com.service.model.entity.User;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class AuthUtils {

    @SneakyThrows
    public User getUserFromAuthHeader(String authHeader) {
        if(Objects.isNull(authHeader) || !authHeader.startsWith("Bearer ")) {
             return new User(null, Role.ANONYMOUS);
        }
        authHeader = authHeader.substring(7); // Remove "Bearer " prefix
        try {
            FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(authHeader);
            return new User(firebaseToken.getUid(), Role.USER);
        } catch (FirebaseAuthException e) {
            //todo: enhance exception
            throw new FirebaseAuthException(e);
        }
    }
}
