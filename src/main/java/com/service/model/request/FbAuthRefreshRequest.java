package com.service.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FbAuthRefreshRequest {

    private String grant_type = "refresh_token";
    private String refresh_token;

    public FbAuthRefreshRequest(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
