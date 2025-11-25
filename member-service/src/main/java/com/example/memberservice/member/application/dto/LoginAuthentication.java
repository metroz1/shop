package com.example.memberservice.member.application.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginAuthentication extends UsernamePasswordAuthenticationToken {

    public LoginAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }
}
