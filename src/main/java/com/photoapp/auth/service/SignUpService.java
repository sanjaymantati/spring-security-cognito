package com.photoapp.auth.service;

import com.photoapp.auth.dto.request.UserSignUpRequestDto;

public interface SignUpService {

    void signUpUser(UserSignUpRequestDto request);
}
