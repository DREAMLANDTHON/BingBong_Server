package com.bingbong.consult.member.domain.repository;

import com.bingbong.consult.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
//    Optional<Member> findByKakaoKey(String kakaoKey);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByEmailAndRole(String email, String role);
}
