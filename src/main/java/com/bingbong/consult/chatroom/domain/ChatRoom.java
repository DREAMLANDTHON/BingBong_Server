package com.bingbong.consult.chatroom.domain;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE ChatRoom SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    private ClassRoom classRoom;
//
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    private Member parent;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime timePin;
    private String roomToken;



}
