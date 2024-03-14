package com.service.model;

import com.service.model.response.FbAuthRefreshResponse;
import com.service.model.response.FbAuthResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ModelMapper {
    public FbAuthResponse mapToFbAuthResponse(FbAuthRefreshResponse refreshResponse) {
        FbAuthResponse fbAuthResponse = new FbAuthResponse();
        fbAuthResponse.setIdToken(refreshResponse.getAccess_token());
        fbAuthResponse.setRefreshToken(refreshResponse.getRefresh_token());
        fbAuthResponse.setLocalId(refreshResponse.getUser_id());
        return fbAuthResponse;
    }
}