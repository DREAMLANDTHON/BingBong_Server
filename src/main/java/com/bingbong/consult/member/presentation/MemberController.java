package com.bingbong.consult.member.presentation;

import com.bingbong.consult.member.application.MemberService;
import com.bingbong.consult.member.presentation.dto.MemberDto;
import com.bingbong.consult.member.presentation.dto.MemberKeyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/members")
    public ResponseEntity<Long> registerMember(@RequestBody MemberDto form) {
        return ResponseEntity.ok(memberService.register(form));
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<MemberDto> getMember(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMember(id));
    }

    @GetMapping("/members/key/{key}")
    public ResponseEntity<MemberKeyDto> getMemberKey(@PathVariable String key) {
        return ResponseEntity.ok(memberService.getMemberByKey(key));
    }
}
