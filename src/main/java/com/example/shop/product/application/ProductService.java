package com.example.shop.product.application;

import com.example.shop.common.ResponseEntity;
import com.example.shop.product.application.dto.ProductCommand;
import com.example.shop.product.application.dto.ProductInfo;
import com.example.shop.product.domain.Product;
import com.example.shop.product.domain.ProductRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ResponseEntity<List<ProductInfo>> findAll(Pageable pageable) {

        Page<Product> page = productRepository.findAll(pageable);
        List<ProductInfo> products = page.stream()
                .map(ProductInfo::from)
                .toList();

        return new ResponseEntity<>(HttpStatus.OK.value(), products, products.size());
    }


    public ResponseEntity<ProductInfo> createProduct(ProductCommand command) {

        Product product = Product.create(
                command.name(),
                command.description(),
                command.price(),
                command.stock(),
                command.status(),
                command.operatorId());

        ProductInfo response = ProductInfo.from(productRepository.save(product));

        return new ResponseEntity<>(HttpStatus.CREATED.value(), response, 1);
    }

    public ResponseEntity<ProductInfo> updateProduct(ProductCommand command, String id) {

        Product product = productRepository.findById(UUID.fromString(id)).orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다."));

        product.update(
                command.name(),
                command.description(),
                command.price(),
                command.stock(),
                command.status(),
                command.operatorId()
        );

        ProductInfo response = ProductInfo.from(productRepository.save(product));

        return new ResponseEntity<>(HttpStatus.OK.value(), response, 1);
    }

    public ResponseEntity<Void> deleteMember(String id) {

        productRepository.findById(UUID.fromString(id)).orElseThrow(() -> new IllegalArgumentException("삭제 대상 상품을 찾을 수 없습니다."));

        productRepository.deleteById(UUID.fromString(id));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT.value(), null, 0L);
    }
}
