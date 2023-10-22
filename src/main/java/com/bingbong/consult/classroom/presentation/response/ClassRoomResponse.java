package com.bingbong.consult.classroom.presentation.response;

import com.bingbong.consult.member.presentation.dto.MemberDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClassRoomResponse {
    Long id;
    String classRoomName;
    String description;
    Integer year;
    String teacher;
    String groupCode;
}
