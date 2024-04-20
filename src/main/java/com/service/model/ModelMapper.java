package com.service.model;

import com.service.model.entity.DetailedProduct;
import com.service.model.entity.UserSession;
import com.service.model.response.FbAuthRefreshResponse;
import com.service.model.response.FbAuthResponse;
import com.service.model.response.UserSessionDTO;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@UtilityClass
public class ModelMapper {
    public FbAuthResponse mapToFbAuthResponse(FbAuthRefreshResponse refreshResponse) {
        FbAuthResponse fbAuthResponse = new FbAuthResponse();
        fbAuthResponse.setIdToken(refreshResponse.getAccess_token());
        fbAuthResponse.setRefreshToken(refreshResponse.getRefresh_token());
        fbAuthResponse.setLocalId(refreshResponse.getUser_id());
        return fbAuthResponse;
    }

    public UserSession mapUserSessionDtoToUserSession(UserSessionDTO userSessionDTO) {
        if(Objects.isNull(userSessionDTO)) {
            return null;
        }

        UserSession userSession = new UserSession();
        userSession.setFavourites(extractMapFromBucket(userSessionDTO.getFavouriteEntries()));
        userSession.setCartEntries(extractMapFromBucket(userSessionDTO.getCartEntries()));
        return userSession;
    }

    private static Map<String, DetailedProduct> extractMapFromBucket(UserSessionDTO.Bucket bucket) {
        if(Objects.isNull(bucket) || Objects.isNull(bucket.getEntries()))
            return null;

        return bucket.getEntries().stream()
                .collect(Collectors.toMap(DetailedProduct::getProductId, entry -> entry));
    }
}