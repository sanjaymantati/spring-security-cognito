package com.photoapp.auth.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignInRequest {

    private String username;
    private String email;
    private String password;
    private String newPassword;
}
