package com.bingbong.consult.member.application;

import com.bingbong.consult.member.domain.Member;
import com.bingbong.consult.member.domain.repository.MemberRepository;
import com.bingbong.consult.member.presentation.dto.MemberDto;
import com.bingbong.consult.member.presentation.dto.MemberKeyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    public Long register(MemberDto form) {
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

    public MemberDto getMember(Long id) {
        Optional<Member> member = memberRepository.findById(id);
        if(member.isPresent()) {
            Member m = member.get();
            return MemberDto.builder()
                    .name(m.getName())
                    .email(m.getEmail())
                    .childName(m.getChildName())
                    .kakaoKey(m.getKakaoKey())
                    .build();
        }
        else{
            throw new RuntimeException("회원 조회 실패");
        }
    }

    public MemberKeyDto getMemberByKey(String key) {
        Optional<Member> byKakaoKey = memberRepository.findByKakaoKey(key);
        if (byKakaoKey.isPresent()) {
            MemberDto memberResponse = MemberDto.builder()
                    .name(byKakaoKey.get().getName())
                    .email(byKakaoKey.get().getEmail())
                    .childName(byKakaoKey.get().getChildName())
                    .kakaoKey(byKakaoKey.get().getKakaoKey())
                    .build();
            return MemberKeyDto.builder()
                    .needRegister(false)
                    .member(memberResponse)
                    .build();
        } else return MemberKeyDto.builder()
                .needRegister(true)
                .build();
    }

}
