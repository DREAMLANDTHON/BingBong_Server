package com.bingbong.consult.kafka.presentation;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest implements Serializable {
    private Long senderId;
    private String message;
    private String roomToken;
    private String type;
    private String timestamp;


//    public MessageRequest(Long senderId, String message, String roomToken, String type){
//        this.senderId=senderId;
//        this.message=message;
//        this.roomToken=roomToken;
//        this.type=type;
//    }

    public static MessageRequest worden(String message, String roomToken, String type){
        return MessageRequest.builder()
                .senderId(77L)
                .roomToken(roomToken)
                .type("message")
                .message(message)
                .build();
    }
}
