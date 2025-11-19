package com.example.shop.controller;

import com.example.shop.member.Member;
import com.example.shop.member.MemberRepository;
import com.example.shop.member.MemberRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Member Controller", description = "회원 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("${api.v1}/members")
public class MemberController {


    private final MemberRepository memberRepository;

    @Operation(summary = "전체 회원 조회", description = "모든 회원의 목록을 조회합니다.")
    @GetMapping
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Operation(summary = "회원 생성", description = "새로운 회원을 등록합니다.")
    @PostMapping
    public Member create(@RequestBody MemberRequest request) {

        Member member = new Member(
                UUID.randomUUID(),
                request.email(),
                request.name(),
                request.password(),
                request.phone(),
                request.saltKey(),
                request.flag()
        );

        return memberRepository.save(member);
    }

    @PutMapping("{id}")
    public Member update(@RequestBody MemberRequest request, @PathVariable String id) {

        Member member = new Member(

                id,
                request.email(),
                request.name(),
                request.password(),
                request.phone(),
                request.saltKey(),
                request.flag()
        );

        return memberRepository.save(member);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        memberRepository.deleteById(UUID.fromString(id));
    }
}
