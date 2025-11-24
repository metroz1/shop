package com.example.shop.product.presentation.dto;

import com.example.shop.product.application.dto.ProductCommand;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductRequest(
        String sellerId,
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        String status,
        String operatorId
) {

    public ProductCommand toCommand() {
        UUID operator = operatorId != null ? UUID.fromString(operatorId) : null;
        UUID seller = sellerId != null ? UUID.fromString(sellerId) : null;
        return new ProductCommand(seller, name, description, price, stock, status, operator);
    }
}
