package com.example.shop.member;

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
