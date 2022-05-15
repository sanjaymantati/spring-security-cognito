package com.photoapp.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.photoapp.auth.security.identityprovider.dto.response.LoginResponseDto;
import com.photoapp.auth.security.identityprovider.enums.IdentityChallenge;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserSignInResponseDto {

    private String accessToken;
    private String idToken;
    private String refreshToken;
    private String tokenType;
    private Integer expiresIn;

    private IdentityChallenge challenge;

    public UserSignInResponseDto(LoginResponseDto responseDto) {
        this.accessToken = responseDto.getAccessToken();
        this.idToken = responseDto.getIdToken();
        this.refreshToken = responseDto.getRefreshToken();
        this.tokenType = responseDto.getTokenType();
        this.expiresIn = responseDto.getExpiresIn();
        this.challenge = responseDto.getChallenge();
    }
}

