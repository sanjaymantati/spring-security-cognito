package com.photoapp.auth.security.identityprovider.service;

import com.photoapp.auth.security.identityprovider.dto.request.LoginUserRequestDto;
import com.photoapp.auth.security.identityprovider.dto.request.SignUpUserRequestDto;
import com.photoapp.auth.security.identityprovider.dto.request.UpdateUserPasswordRequestDto;
import com.photoapp.auth.security.identityprovider.dto.response.LoginResponseDto;
import com.photoapp.auth.security.identityprovider.dto.response.SignUpUserResponseDto;
import com.photoapp.auth.security.identityprovider.dto.response.UpdateUserPasswordResponseDto;

public interface IdentityProviderService {


    SignUpUserResponseDto createUser(SignUpUserRequestDto requestDto);

    UpdateUserPasswordResponseDto updateUserPassword(UpdateUserPasswordRequestDto requestDto);


    LoginResponseDto loginUser(LoginUserRequestDto requestDto);

}
