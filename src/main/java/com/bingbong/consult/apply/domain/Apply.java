package com.bingbong.consult.apply.domain;

import com.bingbong.consult.chatroom.domain.ChatRoom;
import com.bingbong.consult.classroom.domain.ClassRoom;
import com.bingbong.consult.member.domain.Member;
import com.bingbong.consult.stomp.ApplyRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Apply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    @JoinColumn(name="member_id")
//    private Member reqMem;
//
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
////    @JoinColumn(name="class_room_id")
//    private ClassRoom classRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom chatRoom;

    private String subject;
    @Enumerated(value = EnumType.STRING)
    private ApplyStatus status;

    public static Apply from(ApplyRequest request, ChatRoom chatRoom) {
        return Apply.builder()
                .chatRoom(chatRoom)
                .subject(request.getSubject())
                .status(ApplyStatus.APPLY)
                .build();
    }
}
