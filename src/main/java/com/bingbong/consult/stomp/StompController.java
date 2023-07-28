package com.bingbong.consult.stomp;
import com.bingbong.consult.apply.application.ApplyService;
import com.bingbong.consult.apply.domain.Apply;
import com.bingbong.consult.chatmessage.application.ChatMessageService;
import com.bingbong.consult.chatmessage.domain.ChatMessage;
import com.bingbong.consult.chatroom.application.ChatRoomService;
import com.bingbong.consult.chatroom.domain.ChatRoom;
import com.bingbong.consult.classroom.application.ClassRoomService;
import com.bingbong.consult.classroom.domain.ClassRoom;
import com.bingbong.consult.evaluation.application.EvaluationService;
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

    @Autowired
    private EvaluationService evaluationService;



    @MessageMapping(value = "/apply")
    public void apply(@RequestBody ApplyRequest request) {
        if (request.getType().equals("apply")) {
            ChatRoom chatRoom = chatRoomService.findMemberAndClassRoom(request);
            System.out.println(chatRoom);
            Apply ret = applyService.save(request, chatRoom);
            if(ret != null) template.convertAndSend("/sub/apply/" + request.getClassId(), ret.getId());
            else template.convertAndSend("/sub/apply/" + request.getClassId(), "Apply Failed");
        }
    }

    @MessageMapping(value = "/session")

    public void start(@RequestBody StartRequest request) {
        if (request.getType().equals("start")) {
            ChatRoom chatRoom = chatRoomService.findChatRoom(request.getChatRoomId());
            if(chatRoom != null) {
                template.convertAndSend("/sub/session/" + chatRoom.getRoomToken(), "start");
            }
            else template.convertAndSend("/sub/session/" + chatRoom.getRoomToken(), "start Failed");
        }
        else if (request.getType().equals("end")){
            ChatRoom chatRoom = chatRoomService.findChatRoom(request.getChatRoomId());
            template.convertAndSend("/sub/session/" + chatRoom.getRoomToken(), "end");
            // 여기에 chat room 의 timePin으로 메세지 가져오고 분석 api 보낸 후 /sub/start에 'end' 보내기
            evaluationService.analyse(chatRoom);
        }
    }

    @MessageMapping(value = "/chat")
    public void message(@RequestBody MessageRequest request) {
        System.out.println(request.getRoomToken());
        if (request.getType().equals("message")) {
            chatMessageService.save(request.getRoomToken(), request);
            template.convertAndSend("/sub/chat/" + request.getRoomToken(), request);
        }
    }
}
