package com.example.shop.product.presentation;

import com.example.shop.common.ResponseEntity;
import com.example.shop.product.application.ProductService;
import com.example.shop.product.application.dto.ProductInfo;
import com.example.shop.product.presentation.dto.ProductRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Product Controller", description = "상품 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("${api.v1}/products")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 목록 조회", description = "등록된 상품을 페이지 단위로 조회한다.")
    @GetMapping
    public ResponseEntity<List<ProductInfo>> findAll(Pageable pageable) {

        return productService.findAll(pageable);
    }

    @Operation(summary = "상품 등록", description = "요청으로 받은 정보를 바탕으로 상품을 신규 등록한다.")
    @PostMapping
    public ResponseEntity<ProductInfo> create(@RequestBody ProductRequest request) {

        return productService.createProduct(request.toCommand());
    }

    @Operation(summary = "상품 수정", description = "상품 ID와 수정 내용을 받아 상품 정보를 변경한다.")
    @PutMapping("{id}")
    public ResponseEntity<ProductInfo> update(@RequestBody ProductRequest request, @PathVariable String id) {

        return productService.updateProduct(request.toCommand(), id);
    }

    @Operation(summary = "상품 삭제", description = "상품 ID에 해당하는 상품을 삭제한다.")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {

        return productService.deleteMember(id);
    }

}
