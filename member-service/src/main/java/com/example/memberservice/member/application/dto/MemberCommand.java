package com.example.memberservice.member.application.dto;

public record MemberCommand(
        String email,
        String name,
        String password,
        String phone,
        String saltKey,
        String flag
) {
}
