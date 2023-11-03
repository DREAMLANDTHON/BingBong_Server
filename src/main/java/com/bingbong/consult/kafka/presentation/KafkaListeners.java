package com.bingbong.consult.kafka.presentation;

import com.bingbong.consult.chatmessage.domain.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @Autowired
    SimpMessagingTemplate template;



    @KafkaListener(topics = "test", groupId = "groupId")
    void Listener(ChatMessage message){
        template.convertAndSend("/sub/chat/"+ message.getRoomToken(), message);
    }
}
