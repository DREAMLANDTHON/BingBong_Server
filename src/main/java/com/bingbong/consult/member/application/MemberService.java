package com.bingbong.consult.member.application;

import com.bingbong.consult.member.domain.Member;
import com.bingbong.consult.member.domain.repository.MemberRepository;
import com.bingbong.consult.member.presentation.dto.MemberDto;
import com.bingbong.consult.member.presentation.dto.MemberKeyDto;
import com.bingbong.consult.security.TokenDto;
import com.bingbong.consult.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    public TokenDto register(MemberDto form) {
        Optional<Member> memberByEmailAndRole = memberRepository.findByEmailAndRole(form.getEmail(), form.getRole());
        if(!memberByEmailAndRole.isPresent()) {
            // 회원가입
            Member member = Member.builder()
                    .name(form.getName())
                    .email(form.getEmail())
                    .childName(form.getChildName())
                    .role(form.getRole())
                    .build();
            memberRepository.save(member);
        }

        Optional<Member> byEmailAndRole = memberRepository.findByEmailAndRole(form.getEmail(), form.getRole());
        String token = createToken(form);
        return TokenDto.builder()
                .token(token)
                .memberId(byEmailAndRole.get().getId())
                .role(form.getRole())
                .build();
    }

    private String createToken(MemberDto form) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(form.getName(), form.getEmail());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return tokenProvider.createToken(authenticationToken, form.getRole());
    }


    public MemberDto getMember(Long id) {
        Optional<Member> member = memberRepository.findById(id);
        if(member.isPresent()) {
            Member m = member.get();
            return MemberDto.builder()
                    .name(m.getName())
                    .email(m.getEmail())
                    .childName(m.getChildName())
                    .role(m.getRole())
                    .build();
        }
        else{
            throw new RuntimeException("회원 조회 실패");
        }
    }

//    public MemberKeyDto getMemberByKey(String key) {
//        Optional<Member> byKakaoKey = memberRepository.findByKakaoKey(key);
//        if (byKakaoKey.isPresent()) {
//            MemberDto memberResponse = MemberDto.builder()
//                    .id(byKakaoKey.get().getId())
//                    .name(byKakaoKey.get().getName())
//                    .email(byKakaoKey.get().getEmail())
//                    .childName(byKakaoKey.get().getChildName())
//                    .role(byKakaoKey.get().getRole())
//                    .build();
//            return MemberKeyDto.builder()
//                    .needRegister(false)
//                    .member(memberResponse)
//                    .build();
//        } else return MemberKeyDto.builder()
//                .needRegister(true)
//                .build();
//    }

    @Transactional(readOnly = true)
    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).get();
    }
}
