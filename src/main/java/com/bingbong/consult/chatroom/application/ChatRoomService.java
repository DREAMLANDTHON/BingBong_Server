package com.bingbong.consult.chatroom.application;

import com.bingbong.consult.chatroom.domain.ChatRoom;
import com.bingbong.consult.chatroom.domain.repo.ChatRoomRepo;
import com.bingbong.consult.classroom.domain.ClassRoom;
import com.bingbong.consult.classroom.domain.repository.ClassRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepo chatRoomRepo;

    @Autowired
    private ClassRoomRepository classRoomRepository;

    public ChatRoom findById(Long id){
        return chatRoomRepo.findById(id).get();
    }

    @Transactional
    public ChatRoom findChatRoom(Long id) {
        ChatRoom ret = this.findById(id);
        return ret;
    }

    @Transactional
    public List<ChatRoom> findChatRoomByClassRoomId(Long id) {
        ClassRoom classRoom = classRoomRepository.findById(id).get();
        List<ChatRoom> ret = chatRoomRepo.findByClassRoomId(id);
        return ret;
    }

    @Transactional
    public ChatRoom findByMemberAndClassRoom(Long memberId, Long classId) {
        return chatRoomRepo.findByMemberAndClassRoom(memberId, classId);
    }
}
