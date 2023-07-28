package com.bingbong.consult.chatmessage.application;

import com.bingbong.consult.chatmessage.domain.ChatMessage;
import com.bingbong.consult.chatmessage.domain.ChatMessageRepo;
import com.bingbong.consult.chatroom.domain.ChatRoom;
import com.bingbong.consult.member.domain.Member;
import com.bingbong.consult.stomp.MessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageService {
    @Autowired
    private ChatMessageRepo chatMessageRepo;

    @Autowired
    private MemberRepo memberRepo;

    public void save(String roomToken, MessageRequest request) {
        ChatRoom chatRoom =  chatMessageRepo.findByRoomToken(roomToken);
        Member member = memberRepo.findById(request.getSenderId());
        chatMessageRepo.save(ChatMessage.from(request, chatRoom, member));

    }
}
