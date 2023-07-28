package com.bingbong.consult.chatmessage.presentation;


import com.bingbong.consult.chatmessage.application.ChatMessageService;
import com.bingbong.consult.chatmessage.domain.ChatMessage;
import com.bingbong.consult.chatmessage.domain.ChatMessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ChatMessageController {

    @Autowired
    private ChatMessageService chatMessageService;

    @GetMapping("/messages/{chatRoomId}")
    public ResponseEntity<List<ChatMessage>> findByChatRoomId(@PathVariable Long chatRoomId){
        List<ChatMessage> res = chatMessageService.findByChatRoomId(chatRoomId);
        return ResponseEntity.ok(res);
    }


}
