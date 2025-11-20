package com.example.shop.seller.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SellerRepository {

    Page<Seller> findAllBy(Pageable pageable);

    Optional<Seller> findById(String id);

    Seller save(Seller seller);

    void deleteById(String id);
}
