package com.bingbong.consult.stomp;
import com.bingbong.consult.apply.application.ApplyService;
import com.bingbong.consult.apply.domain.Apply;
import com.bingbong.consult.chatmessage.application.ChatMessageService;
import com.bingbong.consult.chatmessage.domain.ChatMessage;
import com.bingbong.consult.chatroom.application.ChatRoomService;
import com.bingbong.consult.chatroom.domain.ChatRoom;
import com.bingbong.consult.classroom.application.ClassRoomService;
import com.bingbong.consult.classroom.domain.ClassRoom;
import com.bingbong.consult.member.application.MemberService;
import com.bingbong.consult.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StompController {

    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달
    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ApplyService applyService;

    @Autowired
    private ClassRoomService classRoomService;



    @MessageMapping(value = "/apply/{classId}")
    public void apply(@PathVariable Long classId, @RequestBody ApplyRequest request) {
        if (request.getType().equals("apply")) {
            ChatRoom chatRoom = chatRoomService.findByMemberAndClassRoom(request.getMemberId(), classId );
            Apply ret = applyService.save(request, chatRoom);
            if(ret != null) template.convertAndSend("/sub/apply/" + classId, ret.getId());
            else template.convertAndSend("/sub/apply/" + classId, "Apply Failed");
        }
    }

    @MessageMapping(value = "/start/{chatRoomId}")
    public void start(@PathVariable Long chatRoomId, @RequestBody StartRequest request) {
        if (request.getType().equals("start")) {
            ChatRoom chatRoom = chatRoomService.findChatRoom(request.getChatRoomId());
            if(chatRoom != null) template.convertAndSend("/sub/start/" + chatRoom.getRoomToken(), "start");
            else template.convertAndSend("/sub/start/" + chatRoom.getRoomToken(), "start Failed");
        }
    }

    @MessageMapping(value = "/chat/{roomToken}")
    public void message(@PathVariable("roomToken") String roomToken, @RequestBody MessageRequest request) {
//        System.out.println(request.getMessage());
        if (request.getType().equals("message")) {
            chatMessageService.save(roomToken, request);
            template.convertAndSend("/sub/chat/" + roomToken, request);
        }
    }
}
