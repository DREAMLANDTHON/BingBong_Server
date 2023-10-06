package com.bingbong.consult.security;

import com.bingbong.consult.member.presentation.dto.MemberDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDto {
    String token;
    String role;
}
