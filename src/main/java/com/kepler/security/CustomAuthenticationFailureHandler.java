package com.kepler.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        RedirectStrategy redirectStrategy = getRedirectStrategy();
        String targetUrl;

        if (exception instanceof UsernameNotFoundException || exception instanceof BadCredentialsException) {
            targetUrl = "/login?incorrectAccount";
        } else if (exception instanceof LockedException) {
            targetUrl = "/login?accountIsLocked";
        } else {
            saveException(request, exception);
            targetUrl = "/login?error";
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

 }
