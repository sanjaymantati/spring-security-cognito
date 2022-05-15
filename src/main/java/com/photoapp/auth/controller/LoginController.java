package com.photoapp.auth.controller;


import com.photoapp.auth.dto.request.UserSignInRequestDto;
import com.photoapp.auth.dto.response.BaseResponseDto;
import com.photoapp.auth.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@Validated
@RequestMapping(path = "/api/users")
@Slf4j
public class LoginController {


    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }


    @PostMapping(path = "/sign-in")
    public @ResponseBody ResponseEntity<BaseResponseDto> signIn(@RequestBody UserSignInRequestDto request) {
        return ResponseEntity.ok(new BaseResponseDto(loginService.loginUser(request)));
    }


    @GetMapping(path = "/user-profile")
    public ResponseEntity<Object> userProfile(Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.accepted().build();
    }
}