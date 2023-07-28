package com.bingbong.consult.chatroom.domain;
import com.bingbong.consult.chatmessage.domain.ChatMessage;
import com.bingbong.consult.classroom.domain.ClassRoom;
import com.bingbong.consult.member.domain.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
//    @Column(name="chat_room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    @JoinColumn(name="class_room_id")
    private ClassRoom classRoom;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name="member_id")
    private Member parent;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime timePin;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn( name = "chat_message_id")
    private List<ChatMessage> chatMessages;

    private String roomToken;



}
