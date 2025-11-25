package com.example.memberservice.member.presentation.dto;

public record LoginRequest(
        String email,
        String password
) {
}
