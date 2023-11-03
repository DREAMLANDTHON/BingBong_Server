package com.bingbong.consult.chatmessage.domain;

import com.bingbong.consult.chatroom.domain.ChatRoom;
import com.bingbong.consult.member.domain.Member;
import com.bingbong.consult.kafka.presentation.MessageRequest;
import com.bingbong.consult.stomp.StartRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
//@SQLDelete(sql = "UPDATE ChatMessage SET deleted = true WHERE id = ?")
//@Where(clause = "deleted = false")
public class ChatMessage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    @JoinColumn(name="chat_room_id")
//    private ChatRoom chatRoom;

    private String roomToken;

//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    @JoinColumn(name="member_id")
    private Long memberId;
    private String type;
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime sendAt;


    public static ChatMessage from(MessageRequest request, Member member) {
        return ChatMessage.builder()
                .roomToken(request.getRoomToken())
                .type(request.getType())
                .memberId(request.getSenderId())
                .message(request.getMessage())
                .sendAt(LocalDateTime.now())
                .build();
    }

    public static ChatMessage warning(StartRequest request, String roomToken) {
        return ChatMessage.builder()
                .roomToken(roomToken)
                .type("warning")
                .memberId(77L)
                .message("채팅을 시작합니다.\n산업 안전 보건법에 따라 고객응대 근로자 보호조치가 시행되고 있습니다.\n서로간의 배려는 보다 따뜻한 세상을 만듭니다.")
                .sendAt(LocalDateTime.now())
                .build();
    }

    public static ChatMessage ending(StartRequest request, String roomToken) {
        return ChatMessage.builder()
                .roomToken(roomToken)
                .type("warning")
                .memberId(77L)
                .message("상담이 종료되었습니다.\n상담내용은 보관되며 상담원 보호용 증거로 채택될 수 있습니다.\n서로간의 배려는 보다 따뜻한 세상을 만듭니다.")
                .sendAt(LocalDateTime.now())
                .build();
    }


//    @Override
//    public String toString() {
//        return "ChatMessage{" +
//                "sender='" + sender + '\'' +
//                ", content='" + content + '\'' +
//                ", timestamp='" + timestamp + '\'' +
//                ", chatRoomID='" + chatRoomId + '\'' +
//                '}';
//    }
}
