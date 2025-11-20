package com.example.shop.seller.infrastructure;


import com.example.shop.seller.domain.Seller;
import com.example.shop.seller.domain.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class SellerRepositoryAdapter implements SellerRepository {

    private final SellerJpaRepository sellerJpaRepository;


    @Override
    public Page<Seller> findAllBy(Pageable pageable) {

        return sellerJpaRepository.findAll(pageable);
    }

    @Override
    public Optional<Seller> findById(String id) {

        return sellerJpaRepository.findById(UUID.fromString(id));
    }

    @Override
    public Seller save(Seller seller) {

        return sellerJpaRepository.save(seller);
    }

    @Override
    public void deleteById(String id) {

        sellerJpaRepository.findById(UUID.fromString(id)).orElseThrow(() -> new IllegalArgumentException("삭제 대상 판매자가 존재하지 않습니다."));

        sellerJpaRepository.deleteById(UUID.fromString(id));
    }
}
