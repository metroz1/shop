package com.example.memberservice.member.application;

import com.example.memberservice.common.ResponseEntity;
import com.example.memberservice.member.application.dto.LoginAuthentication;
import com.example.memberservice.member.application.dto.MemberCommand;
import com.example.memberservice.member.application.dto.MemberInfo;
import com.example.memberservice.member.domain.Member;
import com.example.memberservice.member.domain.MemberRepository;
import com.example.memberservice.member.presentation.dto.LoginRequest;
import com.example.memberservice.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.FailedLoginException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public ResponseEntity<List<MemberInfo>> findAll(Pageable pageable) {

        Page<Member> page = memberRepository.findAllBy(pageable);

        List<MemberInfo> memberInfos = page.stream().map(MemberInfo::from).toList();

        return new ResponseEntity<>(HttpStatus.OK.value(), memberInfos, page.getTotalElements());
    }

    public ResponseEntity<MemberInfo> createMember(MemberCommand command) {

        String encodedPassword = passwordEncoder.encode(command.password());

        Member member = Member.create(
                command.email(),
                command.name(),
                encodedPassword,
                command.phone()
        );

        MemberInfo response = MemberInfo.from(memberRepository.save(member));
        
        return new ResponseEntity<>(HttpStatus.CREATED.value(), response, 1);
    }

    public ResponseEntity<MemberInfo> updateMember(MemberCommand command, String id) {

        Member member = memberRepository.findById(UUID.fromString(id)).orElseThrow(() -> new IllegalArgumentException("해당 ID를 찾을 수 없습니다."));
        String password = command.password();
        String encodedPassword = password == null || password.isBlank() ? member.getPassword() : passwordEncoder.encode(password);

        member.updateMember(command, encodedPassword);
        MemberInfo response = MemberInfo.from(memberRepository.save(member));

        return new ResponseEntity<>(HttpStatus.OK.value(), response, 1);
    }

    public ResponseEntity<Void> deleteMember(String id) {

        Member member = memberRepository.findById(UUID.fromString(id)).orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다 : " + id));
        memberRepository.deleteById(UUID.fromString(id));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT.value(), null, 0L);
    }

    public ResponseEntity<HashMap<String, Object>> login(LoginRequest loginRequest) throws FailedLoginException {

        Member member = memberRepository.findByEmail(loginRequest.email()).orElseThrow(() -> new IllegalArgumentException("해당 이메일과 일치하는 정보가 존재하지 않습니다."));
        HashMap<String, Object> response = new HashMap<>();

        if (passwordEncoder.matches(loginRequest.password(), member.getPassword())) {

            Authentication authentication = new LoginAuthentication(member.getId(), null);

            String accessToken = jwtProvider.generateAccessToken(authentication);
            String refreshToken = jwtProvider.generateRefreshToken(authentication);

            response.put("token", accessToken);
            response.put("refresh-token", refreshToken);

        } else {
            throw new FailedLoginException("password is not correct");
        }

        return new ResponseEntity<>(HttpStatus.OK.value(), response, 1);
    }

    public ResponseEntity<HashMap<String, Object>> refreshToken(String refreshToken) {

        String subject = jwtProvider.getUserDataFromJwt(refreshToken);
        UUID id = UUID.fromString(subject);
        HashMap<String, Object> response = new HashMap<>();

        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("일치하는 회원 정보가 없습니다."));

        Authentication authentication = new LoginAuthentication(subject, null);
        response.put("access-token", jwtProvider.generateAccessToken(authentication));

        return new ResponseEntity<>(HttpStatus.OK.value(), response, 1);
    }

    public Boolean check(String httpMethod, String requestPath) {
        return true;
    }
}
