package com.bingbong.consult.chatBot.presentation;

import com.bingbong.consult.chatBot.application.ChatBotService;
import com.bingbong.consult.chatBot.domain.repository.ChatBot;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChatBotController {
    private final ChatBotService chatBotService;

    @GetMapping("/chatBots")
    public ResponseEntity<ChatBot> findAnswer(@RequestParam("question") String question) {
        return ResponseEntity.ok(chatBotService.findAnswer(question));
    }
}
