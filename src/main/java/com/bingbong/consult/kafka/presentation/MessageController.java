package com.bingbong.consult.kafka.presentation;
import com.bingbong.consult.chatmessage.application.ChatMessageService;
import com.bingbong.consult.chatmessage.domain.ChatMessage;
import com.bingbong.consult.member.application.MemberService;
import com.bingbong.consult.member.domain.Member;
import com.bingbong.consult.mlp.GoogleCloudTextAnalysis;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javafx.scene.canvas.GraphicsContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Controller
@RequestMapping("/pub")
@SecurityRequirement(name = "Bearer Authentication")
public class MessageController {
    @Autowired
    private KafkaTemplate<String, ChatMessage> kafkaTemplate;

//    @Autowired
//    private MemberService memberService;

    @Autowired
    private ChatMessageService chatMessageService;

    @MessageMapping(value = "/chat")
    public void sendMessage(@RequestBody MessageRequest request) {
        request.setTimestamp(LocalDateTime.now().toString());
        if (request.getType().equals("message")) {
            try {
                Float profanity = GoogleCloudTextAnalysis.analyze(request.getMessage()).get("Profanity");
                if (profanity > 0.9f) {
                    request.setMessage("Worden이 부적절한 메세지를 숨김처리하였습니다.");
                    request.setType("warning");
                }
                ChatMessage message = chatMessageService.save(request);
                kafkaTemplate.send("test", message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

