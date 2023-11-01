package com.bingbong.consult.classroom.presentation.request;

import com.bingbong.consult.classroom.domain.ClassRoom;
import com.bingbong.consult.classroom.posts.Post;
import lombok.Data;

@Data
public class PostRequest {
     private String title;
    private String content;
     private Long classRoomId;

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .classRoom(ClassRoom.builder().build())
                .build();
    }
}
