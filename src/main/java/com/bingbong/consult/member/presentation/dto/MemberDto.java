package com.bingbong.consult.member.presentation.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberDto {
    Long id;
    String name;
    String email;
    String childName;
    String kakaoKey;
    String role; // PARENT, TEACHER
}
