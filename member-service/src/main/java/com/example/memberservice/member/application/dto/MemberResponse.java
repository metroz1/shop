package com.example.memberservice.member.application.dto;

import com.example.memberservice.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Schema(description = "회원 응답 DTO")
public record MemberResponse(

        @Schema(description = "회원 식별 ID", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID id,

        @Schema(description = "이메일 주소", example = "user@example.com")
        String email,

        @Schema(description = "회원 이름", example = "홍길동")
        String name,

        @Schema(description = "전화번호", example = "010-1234-5678")
        String phone,

        @Schema(description = "등록자 ID")
        UUID regId,

        @Schema(description = "등록 일시")
        LocalDateTime regDt,

        @Schema(description = "수정자 ID")
        UUID modifyId,

        @Schema(description = "수정 일시")
        LocalDateTime modifyDt
) {
    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .phone(member.getPhone())
                .regId(member.getRegId())
                .regDt(member.getRegDt())
                .modifyId(member.getModifyId())
                .modifyDt(member.getModifyDt())
                .build();
    }
}
