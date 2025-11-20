package com.example.shop.payment.application;

import com.example.shop.common.ResponseEntity;
import com.example.shop.payment.application.dto.PaymentCommand;
import com.example.shop.payment.application.dto.PaymentInfo;
import com.example.shop.payment.client.TossPaymentClient;
import com.example.shop.payment.client.TossPaymentResponse;
import com.example.shop.payment.domain.Payment;
import com.example.shop.payment.domain.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
//    private final PaymentFailureRepository paymentFailureRepository;
//    private final SellerSettlementRepository sellerSettlementRepository;
    private final TossPaymentClient tossPaymentClient;
//    private final OrderService orderService;

    public ResponseEntity<List<PaymentInfo>> findAll(Pageable pageable) {

        Page<Payment> page = paymentRepository.findAllBy(pageable);

        List<PaymentInfo> paymentInfos = page.stream().map(PaymentInfo::from).toList();

        return new ResponseEntity<>(HttpStatus.OK.value(), paymentInfos, page.getTotalElements());
    }

    public ResponseEntity<PaymentInfo> confirm(PaymentCommand command) {

        TossPaymentResponse tossPaymentResponse = tossPaymentClient.confirm(command);

        Payment payment = Payment.create(
                tossPaymentResponse.paymentKey(),
                tossPaymentResponse.orderId(),
                tossPaymentResponse.totalAmount()
        );

        LocalDateTime approvedAt = tossPaymentResponse.approvedAt() != null ? tossPaymentResponse.approvedAt().toLocalDateTime() : null;
        LocalDateTime requestedAt = tossPaymentResponse.requestedAt() != null ? tossPaymentResponse.requestedAt().toLocalDateTime() : null;
        payment.markConfirmed(tossPaymentResponse.method(), approvedAt, requestedAt);

        Payment savedPayment = paymentRepository.save(payment);

        return new ResponseEntity<>(HttpStatus.OK.value(), PaymentInfo.from(savedPayment), 1);
    }




}
