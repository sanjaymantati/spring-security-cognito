package com.photoapp.auth.security.identityprovider.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class SignUpUserRequestDto implements Serializable {

    private String username;
    private String password;
    private String email;

    public SignUpUserRequestDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
