package com.bingbong.consult.chatBot.application;

import com.bingbong.consult.chatBot.domain.repository.ChatBot;
import com.bingbong.consult.chatBot.domain.repository.ChatBotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatBotService {
    private final ChatBotRepository chatBotRepository;

    public ChatBot findAnswer(String question) {
        List<ChatBot> chatBotList = chatBotRepository.findByQuestionLike(question);
        if (chatBotList.isEmpty()) {
            return null;
        }
        return chatBotList.get(0);
    }
}
