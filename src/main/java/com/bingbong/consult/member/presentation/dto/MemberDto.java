package com.bingbong.consult.member.presentation.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberDto {
    String name;
    String email;
    String childName;
    String role; // PARENT, TEACHER
}
