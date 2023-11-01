package com.bingbong.consult.chatmessage.presentation;


import com.bingbong.consult.chatmessage.application.ChatMessageService;
import com.bingbong.consult.chatmessage.domain.ChatMessage;
import com.bingbong.consult.chatmessage.domain.ChatMessageRepo;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
@SecurityRequirement(name = "Bearer Authentication")
public class ChatMessageController {

    @Autowired
    private ChatMessageService chatMessageService;

    @GetMapping("/messages/{chatRoomToken}")
    public ResponseEntity<List<ChatMessage>> findByRoomToken(@PathVariable String chatRoomToken){
        List<ChatMessage> res = chatMessageService.findByRoomToken(chatRoomToken);
        return ResponseEntity.ok(res);
    }


}
