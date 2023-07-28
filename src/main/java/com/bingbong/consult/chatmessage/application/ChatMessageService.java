package com.bingbong.consult.chatmessage.application;

import com.bingbong.consult.chatmessage.domain.ChatMessage;
import com.bingbong.consult.chatmessage.domain.ChatMessageRepo;

import com.bingbong.consult.chatroom.domain.ChatRoom;
import com.bingbong.consult.chatroom.domain.repo.ChatRoomRepo;
import com.bingbong.consult.member.domain.Member;
import com.bingbong.consult.member.domain.repository.MemberRepository;
import com.bingbong.consult.stomp.MessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;



@Service
public class ChatMessageService {
    @Autowired
    private ChatMessageRepo chatMessageRepo;

    @Autowired
    private ChatRoomRepo chatRoomRepo;

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    public void save(String roomToken, MessageRequest request) {
        ChatRoom chatRoom =  chatRoomRepo.findByRoomToken(roomToken);
        Member member = memberRepository.findById(request.getSenderId()).get();
        chatMessageRepo.save(ChatMessage.from(request, chatRoom, member));

    }
}
