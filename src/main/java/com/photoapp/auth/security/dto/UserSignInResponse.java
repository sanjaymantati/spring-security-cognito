package com.photoapp.auth.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignInResponse {


    private String accessToken;
    private String idToken;
    private String refreshToken;
    private String tokenType;
    private Integer expiresIn;
}
