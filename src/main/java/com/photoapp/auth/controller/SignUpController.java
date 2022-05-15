package com.photoapp.auth.controller;

import com.photoapp.auth.dto.request.UserSignUpRequestDto;
import com.photoapp.auth.service.SignUpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping(path = "/api/users")
@Slf4j
public class SignUpController {


    private final SignUpService signUpService;


    public SignUpController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @PostMapping(path = "/sign-up")
    public void signUp(@RequestBody @Valid UserSignUpRequestDto request) {
        log.debug("Initiating sign up.");
        this.signUpService.signUpUser(request);
    }
}
