package com.photoapp.auth.security.identityprovider.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginUserRequestDto {

    private String username;
    private String password;
}
