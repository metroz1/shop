package com.example.shop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Operation(summary = "테스트", description = "헬로 문자열을 반환하는 테스트용 엔드포인트")
    @ApiResponse
    @GetMapping("/")
    public String test() {
        return "hello";
    }

}
