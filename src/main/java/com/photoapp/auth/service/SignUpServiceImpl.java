package com.photoapp.auth.service;

import com.photoapp.auth.dto.request.UserSignUpRequestDto;
import com.photoapp.auth.entity.User;
import com.photoapp.auth.exception.CustomException;
import com.photoapp.auth.repository.UserRepository;
import com.photoapp.auth.security.identityprovider.dto.request.SignUpUserRequestDto;
import com.photoapp.auth.security.identityprovider.dto.request.UpdateUserPasswordRequestDto;
import com.photoapp.auth.security.identityprovider.dto.response.SignUpUserResponseDto;
import com.photoapp.auth.security.identityprovider.enums.IdentityChallenge;
import com.photoapp.auth.security.identityprovider.service.IdentityProviderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.photoapp.auth.utility.Utility.notEmpty;

@Service
@Slf4j
@AllArgsConstructor
@Transactional(readOnly = true)
public class SignUpServiceImpl implements SignUpService {


    private final IdentityProviderService identityProviderService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void signUpUser(UserSignUpRequestDto request) {

        this.userRepository.findByUsername(request.getUsername())
                .ifPresent(r -> {new CustomException("Username already exists");});

        log.debug("creating user {} ...", request.getUsername());
        SignUpUserResponseDto responseDto = this.identityProviderService.createUser(new SignUpUserRequestDto(request.getUsername(), request.getPassword(), request.getEmail()));
        log.info("User {} created.", request.getUsername());
        if (notEmpty(responseDto.getChallenge()))
            this.resolveSignUpUserChallenge(request, responseDto.getChallenge());

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(this.passwordEncoder.encode(request.getPassword()));
        user.setUsername(request.getUsername());
        this.userRepository.save(user);
    }

    private void resolveSignUpUserChallenge(UserSignUpRequestDto request, IdentityChallenge challenge) {
        log.info("Challenge for user {} is raised: {} ", request.getUsername(), challenge);
        if (IdentityChallenge.UPDATE_PASSWORD.equals(challenge)) {
            log.debug("Updating password for user {}", request.getUsername());
            this.identityProviderService.updateUserPassword(new UpdateUserPasswordRequestDto(request.getUsername(), request.getPassword()));
        }
    }
}
