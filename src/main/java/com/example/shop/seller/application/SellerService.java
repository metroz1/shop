package com.example.shop.seller.application;

import com.example.shop.common.ResponseEntity;
import com.example.shop.member.application.dto.MemberInfo;
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

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerRepository sellerRepository;

    public ResponseEntity<List<SellerInfo>> findAll(Pageable pageable) {

        Page<Seller> page = sellerRepository.findAllBy(pageable);

        List<SellerInfo> sellerInfos = page.stream().map(SellerInfo::from).toList();

        return new ResponseEntity<>(HttpStatus.OK.value(), sellerInfos, sellerInfos.size());
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
}
