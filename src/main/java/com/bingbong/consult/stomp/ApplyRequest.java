package com.bingbong.consult.stomp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApplyRequest {
    private Long memberId;
    private Long chatRoomId;
    private String subject;
    private Long classId;
    private String type;
}
