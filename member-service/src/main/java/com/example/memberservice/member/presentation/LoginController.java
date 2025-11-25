package com.example.memberservice.member.presentation;

import com.example.memberservice.common.ResponseEntity;
import com.example.memberservice.member.application.MemberService;
import com.example.memberservice.member.presentation.dto.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.FailedLoginException;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @PostMapping("${api.v1}/login")
    public ResponseEntity<HashMap<String, Object>> login(@RequestBody LoginRequest loginRequest) throws FailedLoginException {

        return memberService.login(loginRequest);
    }

    @GetMapping("${api.v1}/authorizations/check")
    public Boolean check(@RequestParam("httpMethod") String httpMethod, @RequestParam("requestPath") String requestPath) {

        return memberService.check(httpMethod, requestPath);
    }

    @GetMapping("${api.v1}/refresh/token")
    public ResponseEntity<HashMap<String, Object>> refreshToken(HttpServletRequest request) {

        return memberService.refreshToken(request.getHeader("refresh-token"));
    }
}
