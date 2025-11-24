package com.example.shop.payment.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository {

    Page<Payment> findAllBy(Pageable pageable);

    Optional<Payment> findById(UUID id);

    Payment save(Payment payment);
}
