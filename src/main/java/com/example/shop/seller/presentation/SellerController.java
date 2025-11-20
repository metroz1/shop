package com.example.shop.seller.presentation;


import com.example.shop.common.ResponseEntity;
import com.example.shop.seller.application.SellerService;
import com.example.shop.seller.application.dto.SellerInfo;
import com.example.shop.seller.presentation.dto.SellerRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Seller Controller", description = "판매자 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("${api.v1}/sellers")
public class SellerController {

    private final SellerService sellerService;

    @GetMapping
    public ResponseEntity<List<SellerInfo>> findAll(Pageable pageable) {

        return sellerService.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity<SellerInfo> create(@RequestBody SellerRequest request) {

        return sellerService.createSeller(request.toCommand());
    }
}
