package com.example.shop.controller;

import com.example.shop.common.ResponseEntity;
import com.example.shop.member.Member;
import com.example.shop.member.MemberListResponse;
import com.example.shop.member.MemberRequest;
import com.example.shop.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Tag(name = "Member Controller", description = "회원 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("${api.v1}/members")
public class MemberController {


    private final MemberService memberService;

    @Operation(summary = "전체 회원 조회", description = "모든 회원의 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<MemberListResponse> findAll() {

        return memberService.getAll();
    }

    @Operation(summary = "회원 생성", description = "새로운 회원을 등록합니다.")
    @PostMapping
    public ResponseEntity<MemberListResponse> create(@RequestBody MemberRequest request) {

        return memberService.createMember(request);
    }

    @Operation(summary = "회원 정보 수정", description = "기존 회원 정보를 수정합니다.")
    @PutMapping("{id}")
    public ResponseEntity<MemberListResponse> update(@RequestBody MemberRequest request, @PathVariable String id) {


        return memberService.updateMember(request, id);
    }

    @Operation(summary = "회원 생성", description = "새로운 회원을 등록합니다.")
    @DeleteMapping("{id}")
    public ResponseEntity<MemberListResponse> delete(@PathVariable String id) {

        return memberService.deleteMember(id);
    }
}
