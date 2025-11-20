package com.example.shop.member.presentation.dto;

import com.example.shop.member.domain.Member;
import com.example.shop.member.application.dto.MemberResponse;

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
