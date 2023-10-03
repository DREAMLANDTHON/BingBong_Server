package com.bingbong.consult.member.presentation;

import com.bingbong.consult.member.application.MemberService;
import com.bingbong.consult.member.presentation.dto.MemberDto;
import com.bingbong.consult.member.presentation.dto.MemberKeyDto;
import com.bingbong.consult.security.TokenDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@SecurityRequirement(name = "Bearer Authentication")
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/members/login")
    public ResponseEntity<TokenDto> registerMember(@RequestBody MemberDto form) {
        String jwt = memberService.register(form);
        return new ResponseEntity(responseHeader(jwt), HttpStatus.CREATED);
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<MemberDto> getMember(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMember(id));
    }

//    @GetMapping("/members/key/{key}")
//    @Deprecated
//    public ResponseEntity<MemberKeyDto> getMemberKey(@PathVariable String key) {
//        return ResponseEntity.ok(memberService.getMemberByKey(key));
//    }

    private HttpHeaders responseHeader(String jwt) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Bearer", "" + jwt);
//        httpHeaders.add("Level", tokenProvider.getAuthentication(jwt).authorities.joinToString { it.authority });
        return httpHeaders;
    }
}
