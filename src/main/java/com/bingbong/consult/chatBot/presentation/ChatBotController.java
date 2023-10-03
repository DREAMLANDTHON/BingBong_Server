package com.bingbong.consult.chatBot.presentation;

import com.bingbong.consult.chatBot.application.ChatBotService;
import com.bingbong.consult.chatBot.domain.repository.ChatBot;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@SecurityRequirement(name = "Bearer Authentication")
public class ChatBotController {
    private final ChatBotService chatBotService;

    @GetMapping("/chatBots")
    public ResponseEntity<ChatBot> findAnswer(@RequestParam("question") String question) {
        return ResponseEntity.ok(chatBotService.findAnswer(question));
    }
}
