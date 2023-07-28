package com.bingbong.consult.chatmessage.domain;

import com.bingbong.consult.chatroom.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepo extends JpaRepository<ChatMessage, Long> {

}
