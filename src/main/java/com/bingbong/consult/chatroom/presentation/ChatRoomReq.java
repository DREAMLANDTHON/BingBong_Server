package com.bingbong.consult.chatroom.presentation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomReq {
    private Long classRoomId;
    private Long parentId;

}
