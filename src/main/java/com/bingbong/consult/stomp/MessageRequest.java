package com.bingbong.consult.stomp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {
    private Long senderId;
    private String roomToken;
    private String type;
    private String message;

    public static MessageRequest worden(String message, String roomToken, String type){
        return MessageRequest.builder()
                .senderId(77L)
                .roomToken(roomToken)
                .type("message")
                .message(message)
                .build();
    }
}
