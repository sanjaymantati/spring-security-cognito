package com.photoapp.auth.service;

import com.photoapp.auth.dto.request.UserSignInRequestDto;
import com.photoapp.auth.dto.response.UserSignInResponseDto;

public interface LoginService {

    UserSignInResponseDto loginUser(UserSignInRequestDto request);
}
