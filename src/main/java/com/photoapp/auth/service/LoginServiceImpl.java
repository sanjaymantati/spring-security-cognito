package com.photoapp.auth.service;

import com.photoapp.auth.dto.request.UserSignInRequestDto;
import com.photoapp.auth.dto.response.UserSignInResponseDto;
import com.photoapp.auth.exception.InvalidCredentialsException;
import com.photoapp.auth.repository.UserRepository;
import com.photoapp.auth.security.identityprovider.dto.request.LoginUserRequestDto;
import com.photoapp.auth.security.identityprovider.dto.response.LoginResponseDto;
import com.photoapp.auth.security.identityprovider.service.IdentityProviderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.photoapp.auth.utility.Utility.notEmpty;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final IdentityProviderService identityProviderService;
    private final UserRepository userRepository;

    @Override
    public UserSignInResponseDto loginUser(UserSignInRequestDto request) {

        this.userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials."));
        LoginResponseDto responseDto = this.identityProviderService.loginUser(new LoginUserRequestDto(request.getUsername(), request.getPassword()));
        return new UserSignInResponseDto(responseDto);
    }

}
