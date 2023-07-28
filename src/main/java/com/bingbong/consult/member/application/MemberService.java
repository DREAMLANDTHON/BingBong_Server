package com.bingbong.consult.member.application;

import com.bingbong.consult.member.domain.Member;
import com.bingbong.consult.member.domain.repository.MemberRepository;
import com.bingbong.consult.member.presentation.request.MemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    public Long register(MemberRequest form) {
        Member member = Member.builder()
                .name(form.getName())
                .email(form.getEmail())
                .childName(form.getChildName())
                .kakaoKey(form.getKakaoKey())
                .build();
        memberRepository.save(member);
        Optional<Member> savedMember = memberRepository.findById(member.getId());
        if(savedMember.isPresent()) {
            return savedMember.get().getId();
        }
        else{
            throw new RuntimeException("회원 가입 실패");
        }
    }
}
