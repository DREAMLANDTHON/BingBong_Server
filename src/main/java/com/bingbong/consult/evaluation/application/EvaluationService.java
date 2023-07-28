package com.bingbong.consult.evaluation.application;

import com.bingbong.consult.chatmessage.domain.ChatMessage;
import com.bingbong.consult.chatmessage.domain.ChatMessageRepo;
import com.bingbong.consult.chatroom.domain.ChatRoom;
import com.bingbong.consult.evaluation.domain.repository.EvaluationRepository;
import com.bingbong.consult.member.domain.Member;
import com.bingbong.consult.mlp.GoogleCloudTextAnalysis;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EvaluationService {
    private final EvaluationRepository evaluationRepository;

    @Autowired
    private ChatMessageRepo chatMessageRepo;

    public Long create(Long memberId, Map<String, Float> analytics) {
        // ChatRoomRepository에서 findById를 통해 ChatRoom을 가져온다.
        //
        // ChatMessageRepository에서 findByChatRoomId를 통해 ChatMessage들을 가져온다.
        // 존재하는지 확인하고, 존재하지 않으면 exception을 던진다.
        // 존재하면
        return 1L;
    }

    @Transactional
    public String analyse(ChatRoom chatRoom) {
        LocalDateTime start = chatRoom.getTimePin();
        chatRoom.update(); // timePin update
        LocalDateTime end = LocalDateTime.now();
        Optional<List<ChatMessage>> messages = chatMessageRepo.findBySendAtGreaterThanEqualAndSendAtLessThanEqual(start, end);
        String ret="";
        ChatMessage temp;
        if(messages.isPresent()){
            Member teacher = messages.get().get(0).getChatRoom().getClassRoom().getTeacher();
            for(int i=0; i<messages.get().size();i++){
                temp = messages.get().get(i);
                if(temp.getMember().getId() != teacher.getId()){
                    ret.concat(" "+temp.getMessage());
                }
            }
            try {
                this.create(messages.get().get(0).getMember().getId(), GoogleCloudTextAnalysis.analyze(ret) );

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
