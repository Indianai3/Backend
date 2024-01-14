package com.service.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailPasswordRequestFb extends FbAuthRequest {
    private String email;
    private String password;
}
