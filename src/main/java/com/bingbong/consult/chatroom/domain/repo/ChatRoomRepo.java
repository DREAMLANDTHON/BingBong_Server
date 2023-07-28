package com.bingbong.consult.chatroom.domain.repo;

import com.bingbong.consult.chatroom.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepo extends JpaRepository<ChatRoom, Long> {
    @Query("select c from ChatRoom c where c.classRoom.id = :id")
    List<ChatRoom> findByClassRoomId(Long id);
}
