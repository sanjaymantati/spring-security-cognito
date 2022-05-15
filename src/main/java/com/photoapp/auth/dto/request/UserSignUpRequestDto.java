package com.photoapp.auth.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserSignUpRequestDto {


    @NotNull(message = "Username is required.")
    private String username;

    @NotNull(message = "Email is required.")
    @Email(message = "Invalid email.")
    private String email;
    @NotBlank(message = "Password is required.")
    private String password;
}
