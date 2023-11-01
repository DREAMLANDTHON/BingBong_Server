package com.bingbong.consult.kafka.presentation;
import com.bingbong.consult.chatmessage.application.ChatMessageService;
import com.bingbong.consult.chatmessage.domain.ChatMessage;
import com.bingbong.consult.mlp.GoogleCloudTextAnalysis;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
//@SecurityRequirement(name = "Bearer Authentication")
@Slf4j
@Controller
@RequestMapping("/pub")
public class MessageController {
    @Autowired
    private KafkaTemplate<String, ChatMessage> kafkaTemplate;

//    @Autowired
//    private MemberService memberService;

    @Autowired
    private ChatMessageService chatMessageService;

    @PostMapping("/chat")
    public void sendMessage(@RequestBody MessageRequest request) {
        request.setTimestamp(LocalDateTime.now().toString());
//        if (request.getType().equals("message")) {
//            try {
//                Float profanity = GoogleCloudTextAnalysis.analyze(request.getMessage()).get("Profanity");
////                Float profanity = 0.0f;
//                if (profanity > 0.9f) {
//                    request.setMessage("Worden이 부적절한 메세지를 숨김처리하였습니다.");
//                    request.setType("warning");
//                }
//                ChatMessage message = chatMessageService.save(request);
//                kafkaTemplate.send("test", message);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        try {
            Float profanity = GoogleCloudTextAnalysis.analyze("임의 메세지").get("Profanity");
//                Float profanity = 0.0f;
            if (profanity > 0.9f) {
                request.setMessage("Worden이 부적절한 메세지를 숨김처리하였습니다.");
                request.setType("warning");
            }
            ChatMessage message = chatMessageService.save(request);
            kafkaTemplate.send("test", message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//
//    @PostMapping(value = "/chat")
//    public void sendMessage(@RequestBody MessageRequest request) {
//        request.setTimestamp(LocalDateTime.now().toString());
//        if (request.getType().equals("message")) {
//            ChatMessage message = chatMessageService.save(request);
//            kafkaTemplate.send("test", message);
//        }
//    }
}

