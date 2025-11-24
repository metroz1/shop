package com.example.shop.settlement.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface SellerSettlementRepository {

    Page<SellerSettlement> findAll(Pageable pageable);

    List<SellerSettlement> findByStatusAndSeller(SettlementStatus settlementStatus, UUID uuid);

    List<SellerSettlement> findByStatus(SettlementStatus settlementStatus);

    void saveAll(List<SellerSettlement> pending);
}
