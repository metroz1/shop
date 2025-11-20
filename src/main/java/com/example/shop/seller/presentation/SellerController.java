package com.example.shop.seller.presentation;


import com.example.shop.common.ResponseEntity;
import com.example.shop.seller.application.SellerService;
import com.example.shop.seller.application.dto.SellerInfo;
import com.example.shop.seller.presentation.dto.SellerRequest;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "판매자 목록 조회", description = "등록된 판매자를 페이지 단위로 조회한다.")
    @GetMapping
    public ResponseEntity<List<SellerInfo>> findAll(Pageable pageable) {

        return sellerService.findAll(pageable);
    }

    @Operation(summary = "판매자 등록", description = "요청으로 받은 정보를 바탕으로 판매자를 신규 등록한다.")
    @PostMapping
    public ResponseEntity<SellerInfo> create(@RequestBody SellerRequest request) {

        return sellerService.createSeller(request.toCommand());
    }

    @Operation(summary = "판매자 수정", description = "판매자 ID와 수정 내용을 받아 판매자 정보를 변경한다.")
    @PutMapping("{id}")
    public ResponseEntity<SellerInfo> update(@RequestBody SellerRequest request, @PathVariable String id) {

        return sellerService.updateSeller(request.toCommand(), id);
    }

    @Operation(summary = "판매자 삭제", description = "판매자 ID에 해당하는 판매자를 삭제한다.")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {

        return sellerService.deleteSeller(id);
    }
}
