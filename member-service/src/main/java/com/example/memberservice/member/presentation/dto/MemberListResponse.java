package com.example.memberservice.member.presentation.dto;

import com.example.memberservice.member.application.dto.MemberResponse;
import com.example.memberservice.member.domain.Member;

import java.util.List;

public record MemberListResponse(
        long count, List<MemberResponse> members
) {
    public static MemberListResponse from(List<Member> members) {

        List<MemberResponse> responses = members.stream()
                .map(MemberResponse::from)
                .toList();

        return new MemberListResponse(responses.size(), responses);
    }
}
