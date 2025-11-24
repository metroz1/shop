package com.example.memberservice.member.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository {

    Page<Member> findAllBy(Pageable pageable);

    Optional<Member> findById(UUID id);

    Member save(Member member);

    void deleteById(UUID id);
}
