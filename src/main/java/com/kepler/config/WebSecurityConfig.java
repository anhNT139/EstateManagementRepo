package com.kepler.config;

import com.kepler.security.CustomAuthenticationFailureHandler;
import com.kepler.security.CustomAuthenticationSuccessHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static com.kepler.constant.SystemConstant.Security.*;
import static com.kepler.constant.SystemConstant.User.MANAGER;
import static org.springframework.http.HttpMethod.*;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                    .disable()
                .authorizeRequests()
                    .mvcMatchers(PERMIT_ALL_URL).permitAll()
                    .mvcMatchers(WEB_URL_REQUIRE_ROLE_MANAGER).hasRole(MANAGER)
                    .mvcMatchers(GET, API_GET_METHOD_REQUIRE_ROLE_MANAGER_URL).hasRole(MANAGER)
                    .mvcMatchers(POST, API_POST_METHOD_REQUIRE_ROLE_MANAGER_URL).hasRole(MANAGER)
                    .mvcMatchers(PUT, API_PUT_METHOD_REQUIRE_ROLE_MANAGER_URL).hasRole(MANAGER)
                    .mvcMatchers(DELETE, API_DELETE_METHOD_REQUIRE_ROLE_MANAGER_URL).hasRole(MANAGER)
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login").permitAll()
                    .usernameParameter("j_username").passwordParameter("j_password")
                    .loginProcessingUrl("/process-login")
                    .successHandler(new CustomAuthenticationSuccessHandler())
                    .failureHandler(new CustomAuthenticationFailureHandler())
                    .and()
                .logout()
                    .logoutUrl("/logout")
                    .deleteCookies("JSESSIONID")
                    .and()
                .exceptionHandling()
                    .accessDeniedPage("/access-denied")
                    .and()
                .sessionManagement()
                    .maximumSessions(1)
                    .expiredUrl("/login?sessionExpired");
    }

}

