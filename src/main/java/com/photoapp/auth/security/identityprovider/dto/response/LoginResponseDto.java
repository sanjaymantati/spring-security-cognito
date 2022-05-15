package com.photoapp.auth.security.identityprovider.dto.response;

import com.photoapp.auth.security.identityprovider.enums.IdentityChallenge;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponseDto {

    private IdentityChallenge challenge;
    private String accessToken;
    private String idToken;
    private String refreshToken;
    private String tokenType;
    private Integer expiresIn;
}
