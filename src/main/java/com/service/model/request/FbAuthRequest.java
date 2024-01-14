package com.service.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FbAuthRequest {
    private String email;
    private String password;
    private boolean returnSecureToken = true;
}
