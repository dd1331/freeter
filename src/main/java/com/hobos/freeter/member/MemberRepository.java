package com.hobos.freeter.member;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
//    Optional<Member> findById(Long userId);

    Optional<Member> findByProviderId(String providerId);

}