package com.service.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FbAuthResponse {
    private String kind;
    @JsonIgnore
    private String localId;
    private String email;
    private String displayName;
    private String idToken;
    private boolean registered;
    private String refreshToken;
    private String expiresId;
}
