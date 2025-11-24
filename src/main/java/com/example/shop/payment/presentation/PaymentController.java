package com.example.shop.payment.presentation;

import com.example.shop.common.ResponseEntity;
import com.example.shop.payment.application.PaymentService;
import com.example.shop.payment.application.dto.PaymentFailureInfo;
import com.example.shop.payment.application.dto.PaymentInfo;
import com.example.shop.payment.presentation.dto.PaymentFailReqeust;
import com.example.shop.payment.presentation.dto.PaymentRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Payment Controller", description = "결제 처리 API")
@RestController
@RequestMapping("${api.v1}/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "결제 내역 조회", description = "등록된 결제 정보를 페이지 단위로 조회한다.")
    @GetMapping
    public ResponseEntity<List<PaymentInfo>> findAll(Pageable pageable) {

        return paymentService.findAll(pageable);
    }

    @Operation(summary = "결제 승인 처리", description = "결제 승인 요청을 확인하고 결제 상태를 확정한다.")
    @PostMapping("/confirm")
    public ResponseEntity<PaymentInfo> confirm(@RequestBody PaymentRequest request) {

        return paymentService.confirm(request.toCommand());
    }

    @Operation(summary = "결제 실패 기록", description = "결제 실패에 대한 정보를 저장한다.")
    @PostMapping("/fail")
    public ResponseEntity<PaymentFailureInfo> fail(@RequestBody PaymentFailReqeust reqeust) {

        return paymentService.recordFailure(reqeust.toCommand());
    }
}
