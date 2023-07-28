package com.bingbong.consult.chatmessage.domain;

import com.bingbong.consult.chatroom.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMessageRepo extends JpaRepository<ChatMessage, Long> {

//    @Query("select m from ChatMessage m where m.sendAt between :start and :end")
    Optional<List<ChatMessage>> findBySendAtGreaterThanEqualAndSendAtLessThanEqual(LocalDateTime startDateTime, LocalDateTime endDateTime);

    @Query("select m from ChatMessage m where m.chatRoom.id = :chatRoomId")
    List<ChatMessage> findByChatRoomId(Long chatRoomId);
}
