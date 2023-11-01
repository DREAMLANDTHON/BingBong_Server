package com.bingbong.consult.chatmessage.application;

import com.bingbong.consult.chatmessage.domain.ChatMessage;
import com.bingbong.consult.chatmessage.domain.ChatMessageRepo;

import com.bingbong.consult.chatroom.domain.ChatRoom;
import com.bingbong.consult.chatroom.domain.repo.ChatRoomRepo;
import com.bingbong.consult.member.domain.Member;
import com.bingbong.consult.member.domain.repository.MemberRepository;
import com.bingbong.consult.kafka.presentation.MessageRequest;
import com.bingbong.consult.stomp.StartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ChatMessageService {
    @Autowired
    private ChatMessageRepo chatMessageRepo;

    @Autowired
    private ChatRoomRepo chatRoomRepo;

    @Autowired
    private MemberRepository memberRepository;



    @Transactional
    public ChatMessage save(MessageRequest request) {
        Member member = memberRepository.findById(1L).get();
        return chatMessageRepo.save(ChatMessage.from(request, member));

    }

//    @Transactional(readOnly = true)
//    public List<ChatMessage> findByChatRoomId(Long chatRoomId) {
//        List<ChatMessage> ret = chatMessageRepo.findByChatRoomId(chatRoomId);
//
//        return ret;
//    }

    @Transactional
    public void warning(StartRequest request) {
        if(request.getType().equals("start")) chatMessageRepo.save(ChatMessage.warning(request));
        else chatMessageRepo.save(ChatMessage.ending(request));
    }

    public List<ChatMessage> findByRoomToken(String chatRoomToken) {
        List<ChatMessage> ret = chatMessageRepo.findByRoomToken(chatRoomToken);
        return ret;
    }
}
