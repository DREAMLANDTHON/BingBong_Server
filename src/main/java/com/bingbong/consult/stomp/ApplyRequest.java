package com.bingbong.consult.stomp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApplyRequest {
    private Long classId;
    private String roomToken;
    private String classRoomToken;
    private Long memberId;
    private String subject;
    private String type;
}
