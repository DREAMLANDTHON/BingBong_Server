package com.bingbong.consult.chatroom.presentation.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatRoomResponse {
    Long id;
    String parentName;
    Long parentId;
    String subject;
    String roomToken;
    boolean session;



}
