package com.example.shop.member.presentation;

import com.example.shop.common.ResponseEntity;
import com.example.shop.member.application.dto.MemberInfo;
import com.example.shop.member.presentation.dto.MemberRequest;
import com.example.shop.member.application.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Member Controller", description = "회원 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("${api.v1}/members")
public class MemberController {


    private final MemberService memberService;

    @Operation(summary = "전체 회원 조회", description = "모든 회원의 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<MemberInfo>> findAll(Pageable pageable) {

        return memberService.findAll(pageable);
    }

    @Operation(summary = "회원 생성", description = "새로운 회원을 등록합니다.")
    @PostMapping
    public ResponseEntity<MemberInfo> create(@RequestBody MemberRequest request) {

        return memberService.createMember(request.toCommand());
    }

    @Operation(summary = "회원 정보 수정", description = "기존 회원 정보를 수정합니다.")
    @PutMapping("{id}")
    public ResponseEntity<MemberInfo> update(@RequestBody MemberRequest request, @PathVariable String id) {


        return memberService.updateMember(request.toCommand(), id);
    }

    @Operation(summary = "회원 삭제", description = "기존 회원 정보를 삭제합니다.")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {

        return memberService.deleteMember(id);
    }
}
