package com.example.shop.service;

import com.example.shop.member.Member;
import com.example.shop.member.MemberListResponse;
import com.example.shop.member.MemberRepository;
import com.example.shop.member.MemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberListResponse getAll() {

        List<Member> members = memberRepository.findAll();

        return MemberListResponse.from(members);
    }

    public MemberListResponse createMember(MemberRequest request) {

        Member member = new Member(
                UUID.randomUUID(),
                request.email(),
                request.name(),
                request.password(),
                request.phone(),
                request.saltKey(),
                request.flag()
        );        
        
        return MemberListResponse.from(List.of(member));
    }

    public MemberListResponse updateMember(MemberRequest request, String id) {

        Member member = memberRepository.findById(UUID.fromString(id)).orElseThrow();
        member.updateMember(request);

        return MemberListResponse.from(List.of(memberRepository.save(member)));
    }

    public void deleteMember(String id) {

        Member member = memberRepository.findById(UUID.fromString(id)).orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다 : " + id));
        memberRepository.deleteById(UUID.fromString(id));
    }
}
