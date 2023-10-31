package com.bingbong.consult.classroom.presentation.request;

import lombok.Data;

@Data
public class PostRequest {
     private String title;
    private String content;
     private Long classRoomId;
}
