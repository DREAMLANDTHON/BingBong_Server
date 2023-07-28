package com.bingbong.consult.member.presentation;

import com.bingbong.consult.member.application.MemberService;
import com.bingbong.consult.member.presentation.request.MemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/member")
    public ResponseEntity<Long> registerMember(@RequestBody MemberRequest form) {
        return ResponseEntity.ok(memberService.register(form));
    }
}
