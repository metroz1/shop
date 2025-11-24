package com.example.shop.seller.application;

import com.example.shop.common.ResponseEntity;
import com.example.shop.seller.application.dto.SellerCommand;
import com.example.shop.seller.application.dto.SellerInfo;
import com.example.shop.seller.domain.Seller;
import com.example.shop.seller.domain.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerRepository sellerRepository;

    public ResponseEntity<List<SellerInfo>> findAll(Pageable pageable) {

        Page<Seller> page = sellerRepository.findAllBy(pageable);

        List<SellerInfo> sellerInfos = page.stream().map(SellerInfo::from).toList();

        return new ResponseEntity<>(HttpStatus.OK.value(), sellerInfos, page.getTotalElements());
    }

    public ResponseEntity<SellerInfo> createSeller(SellerCommand command) {

        Seller seller = Seller.register(
                command.companyName(),
                command.representativeName(),
                command.email(),
                command.phone(),
                command.businessNumber(),
                command.address(),
                command.status()
        );

        SellerInfo response = SellerInfo.from(sellerRepository.save(seller));

        return new ResponseEntity<>(HttpStatus.CREATED.value(), response, 1);
    }

    public ResponseEntity<SellerInfo> updateSeller(SellerCommand command, String id) {

        Seller seller = sellerRepository.findById(UUID.fromString(id)).orElseThrow(() -> new IllegalArgumentException("수정 대상 판매자 정보가 존재하지 않습니다."));

        seller.update(
                command.companyName(),
                command.representativeName(),
                command.email(),
                command.phone(),
                command.businessNumber(),
                command.address(),
                command.status()
        );

        SellerInfo response = SellerInfo.from(sellerRepository.save(seller));

        return new ResponseEntity<>(HttpStatus.OK.value(), response, 1);
    }

    public ResponseEntity<Void> deleteSeller(String id) {

        sellerRepository.findById(UUID.fromString(id)).orElseThrow(() -> new IllegalArgumentException("삭제 대상 판매자 정보가 존재하지 않습니다."));

        sellerRepository.deleteById(UUID.fromString(id));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT.value(), null, 0L);
    }
}
