package com.example.memberservice.member.application;

import com.example.memberservice.common.ResponseEntity;
import com.example.memberservice.member.application.dto.MemberCommand;
import com.example.memberservice.member.application.dto.MemberInfo;
import com.example.memberservice.member.domain.Member;
import com.example.memberservice.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public ResponseEntity<List<MemberInfo>> findAll(Pageable pageable) {

        Page<Member> page = memberRepository.findAllBy(pageable);

        List<MemberInfo> memberInfos = page.stream().map(MemberInfo::from).toList();

        return new ResponseEntity<>(HttpStatus.OK.value(), memberInfos, page.getTotalElements());
    }

    public ResponseEntity<MemberInfo> createMember(MemberCommand request) {

        Member member = Member.create(
                request.email(),
                request.name(),
                request.password(),
                request.phone(),
                request.saltKey(),
                request.flag()
        );

        MemberInfo response = MemberInfo.from(memberRepository.save(member));
        
        return new ResponseEntity<>(HttpStatus.CREATED.value(), response, 1);
    }

    public ResponseEntity<MemberInfo> updateMember(MemberCommand request, String id) {

        Member member = memberRepository.findById(UUID.fromString(id)).orElseThrow(() -> new IllegalArgumentException("해당 ID를 찾을 수 없습니다."));
        member.updateMember(request);
        MemberInfo response = MemberInfo.from(memberRepository.save(member));

        return new ResponseEntity<>(HttpStatus.OK.value(), response, 1);
    }

    public ResponseEntity<Void> deleteMember(String id) {

        Member member = memberRepository.findById(UUID.fromString(id)).orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다 : " + id));
        memberRepository.deleteById(UUID.fromString(id));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT.value(), null, 0L);
    }
}
