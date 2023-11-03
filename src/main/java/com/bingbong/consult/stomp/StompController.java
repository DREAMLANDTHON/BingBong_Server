package com.bingbong.consult.stomp;
import com.bingbong.consult.apply.application.ApplyService;
import com.bingbong.consult.apply.domain.Apply;
import com.bingbong.consult.chatmessage.application.ChatMessageService;
import com.bingbong.consult.chatroom.application.ChatRoomService;
import com.bingbong.consult.chatroom.domain.ChatRoom;
import com.bingbong.consult.classroom.application.ClassRoomService;
import com.bingbong.consult.evaluation.application.EvaluationService;
import com.bingbong.consult.kafka.presentation.MessageRequest;
import com.bingbong.consult.member.application.MemberService;
import com.bingbong.consult.mlp.GoogleCloudTextAnalysis;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
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
            if(applyService.findApplyExist(request)) template.convertAndSend("/sub/apply/" + request.getClassRoomToken(), "이전 민원요청이 대기중입니다.");
            else {
//                Apply ret = applyService.save(request);
//                if(ret != null) template.convertAndSend("/sub/apply/" + request.getClassRoomToken(), "요청이 저장되었습니다");
            }
        }
    }

    @MessageMapping(value = "/session")
    public void start(@RequestBody StartRequest request) {
        ChatRoom chatRoom = chatRoomService.sessionUpdate(request);
        if(chatRoom == null) template.convertAndSend("/sub/chat/" + chatRoom.getRoomToken(), "채팅방이 없습니다");
        else {
            chatMessageService.warning(request);
            template.convertAndSend("/sub/chat/" + chatRoom.getRoomToken(), request);
            // 구글 활성화 후 주석제거
            //            if(request.getType().equals("end")) evaluationService.analyse(chatRoom);
        }
    }
}
