package com.photoapp.auth.security.service;

import com.photoapp.auth.security.dto.AuthenticatedUserDetails;

public interface AuthenticationUserDataService {

    public AuthenticatedUserDetails getUserDetails();
}
