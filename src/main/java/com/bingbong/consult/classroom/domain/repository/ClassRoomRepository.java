package com.bingbong.consult.classroom.domain.repository;

import com.bingbong.consult.chatroom.domain.ChatRoom;
import com.bingbong.consult.classroom.domain.ClassRoom;
import com.bingbong.consult.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {
    Optional<ClassRoom> findByGroupCode(String groupCode);

    List<ClassRoom> findAllByTeacher(Member member);
}
