package com.example.shop.service;

import com.example.shop.common.ResponseEntity;
import com.example.shop.member.Member;
import com.example.shop.member.MemberListResponse;
import com.example.shop.member.MemberRepository;
import com.example.shop.member.MemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public ResponseEntity<MemberListResponse> getAll() {

        List<Member> members = memberRepository.findAll();

        MemberListResponse response = MemberListResponse.from(members);

        return new ResponseEntity<>(HttpStatus.OK.value(), response, response.count());
    }

    public ResponseEntity<MemberListResponse> createMember(MemberRequest request) {

        Member member = new Member(
                UUID.randomUUID(),
                request.email(),
                request.name(),
                request.password(),
                request.phone(),
                request.saltKey(),
                request.flag()
        );

        MemberListResponse response = MemberListResponse.from(List.of(memberRepository.save(member)));
        
        return new ResponseEntity<>(HttpStatus.OK.value(), response, response.count());
    }

    public ResponseEntity<MemberListResponse> updateMember(MemberRequest request, String id) {

        Member member = memberRepository.findById(UUID.fromString(id)).orElseThrow();
        member.updateMember(request);
        MemberListResponse response = MemberListResponse.from(List.of(memberRepository.save(member)));

        return new ResponseEntity<>(HttpStatus.OK.value(), response, response.count());
    }

    public ResponseEntity<MemberListResponse> deleteMember(String id) {

        Member member = memberRepository.findById(UUID.fromString(id)).orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다 : " + id));
        memberRepository.deleteById(UUID.fromString(id));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT.value(), null, 0L);
    }
}
