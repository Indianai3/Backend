package com.service.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FbAuthRefreshResponse {
    private String access_token;
    private String expires_in;
    private String token_type;
    private String refresh_token;
    private String id_token;
    private String user_id;
    private String project_id;
}