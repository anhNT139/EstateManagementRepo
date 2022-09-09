package com.kepler.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.kepler.constant.SystemConstant.User.ROLE_STAFF;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {
    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public MyUserDetail getPrincipal() {
        return (MyUserDetail) getAuthentication().getPrincipal();
    }

    @Override
    public List<String> getAuthorities() {
        return getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return getAuthentication().getName();
    }

    @Override
    public Long getCurrentLoggedInStaffId() {
        List<String> authorities = getAuthorities();
        Long currentLoggedInStaffId = null;
        if (authorities.size() == 1 && authorities.get(0).equals(ROLE_STAFF)) {
            currentLoggedInStaffId = getPrincipal().getId();
        }
        return currentLoggedInStaffId;
    }
}
