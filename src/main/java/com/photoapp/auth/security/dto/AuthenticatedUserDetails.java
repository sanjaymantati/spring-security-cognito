package com.photoapp.auth.security.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class AuthenticatedUserDetails {

    private String firstName;
    private String lastName;
    private String email;
    private Set<String> roles;
    private Map<String, String> attributes;
}
