package com.example.shop.payment.presentation;

import com.example.shop.common.ResponseEntity;
import com.example.shop.payment.application.PaymentService;
import com.example.shop.payment.application.dto.PaymentInfo;
import com.example.shop.payment.presentation.dto.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.v1}/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<PaymentInfo>> findAll(Pageable pageable) {

        return paymentService.findAll(pageable);
    }

    @PostMapping("/confirm")
    public ResponseEntity<PaymentInfo> confirm(@RequestBody PaymentRequest request) {

        return paymentService.confirm(request.toCommand());
    }
}
