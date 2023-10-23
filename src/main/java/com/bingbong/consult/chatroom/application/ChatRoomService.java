package com.bingbong.consult.chatroom.application;

import com.bingbong.consult.apply.domain.Apply;
import com.bingbong.consult.apply.domain.repo.ApplyRepo;
import com.bingbong.consult.chatroom.domain.ChatRoom;
import com.bingbong.consult.chatroom.domain.repo.ChatRoomRepo;
import com.bingbong.consult.chatroom.presentation.response.ChatRoomResponse;
import com.bingbong.consult.classroom.domain.ClassRoom;
import com.bingbong.consult.classroom.domain.repository.ClassRoomRepository;
import com.bingbong.consult.stomp.ApplyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepo chatRoomRepo;

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Autowired
    private ApplyRepo applyRepo;

    @Transactional
    public ChatRoom findById(Long id){
        return chatRoomRepo.findById(id).get();
    }

    @Transactional
    public ChatRoom findChatRoom(Long id) {
        ChatRoom ret = this.findById(id);
        return ret;
    }

    @Transactional
    public List<ChatRoomResponse> findChatRoomByClassRoomId(Long id) {
        return chatRoomRepo.findByClassRoomId(id)
                .stream().map(chatRoom -> {
                    Optional<Apply> applyInfo = applyRepo.findFirstByChatRoomOrderByCreatedAtDesc(chatRoom);
                    String subject = "아직 신청된 주제가 없습니다.";
                    if(applyInfo.isPresent()) subject = applyInfo.get().getSubject();
                    return ChatRoomResponse.builder()
                            .id(chatRoom.getId())
                            .parentName(chatRoom.getParent().getName())
                            .subject(subject)
                            .build();
                }).collect(Collectors.toList());
    }

    @Transactional
    public ChatRoom findMemberAndClassRoom(ApplyRequest request) {
        return chatRoomRepo.findMemberAndClassRoom(request.getMemberId(), request.getClassId());
    }

}
