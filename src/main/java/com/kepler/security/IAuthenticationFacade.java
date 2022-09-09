package com.kepler.security;

import org.springframework.security.core.Authentication;

import java.util.List;

public interface IAuthenticationFacade {
    Authentication getAuthentication();
    MyUserDetail getPrincipal();
    List<String> getAuthorities();
    String getUsername();
    Long getCurrentLoggedInStaffId();
}
