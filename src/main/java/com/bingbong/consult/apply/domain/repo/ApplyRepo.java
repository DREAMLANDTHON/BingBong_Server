package com.bingbong.consult.apply.domain.repo;

import com.bingbong.consult.apply.domain.Apply;
import com.bingbong.consult.apply.presentation.ApplyDto;
import com.bingbong.consult.chatroom.domain.ChatRoom;
import com.bingbong.consult.classroom.domain.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApplyRepo extends JpaRepository<Apply, Long> {

    @Query("select a from Apply a where a.chatRoom.classRoom = :classRoom order by a.createdAt desc")
    List<Apply> findAllByClassroomOrderByTimePinDesc(ClassRoom classRoom);

    Optional<Apply> findFirstByChatRoomOrderByCreatedAtDesc(ChatRoom chatRoom);

    @Query("select a from Apply a where a.chatRoom.roomToken = :roomToken and a.chatRoom.parent.id = :memberId")
    Optional<Apply> findByRoomTokenAndMemberId(String roomToken, Long memberId);

}
