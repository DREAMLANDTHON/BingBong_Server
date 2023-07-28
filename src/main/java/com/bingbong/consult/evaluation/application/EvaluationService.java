package com.bingbong.consult.evaluation.application;

import com.bingbong.consult.chatmessage.domain.ChatMessage;
import com.bingbong.consult.chatmessage.domain.ChatMessageRepo;
import com.bingbong.consult.chatroom.domain.ChatRoom;
import com.bingbong.consult.evaluation.domain.Evaluation;
import com.bingbong.consult.evaluation.domain.repository.EvaluationRepository;
import com.bingbong.consult.evaluation.presentation.response.AnalyticsReportResponse;
import com.bingbong.consult.member.domain.Member;
import com.bingbong.consult.mlp.GoogleCloudTextAnalysis;
import com.bingbong.consult.member.domain.repository.MemberRepository;
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
    private final MemberRepository memberRepository;
    public Long create(Long memberId, Map<String, Float> analytics) {
        Member parent = memberRepository.findById(memberId).get();
        Evaluation evaluation = Evaluation.builder()
                .toxic(analytics.get("Toxic"))
                .insult(analytics.get("Insult"))
                .profanity(analytics.get("Profanity"))
                .derogatory(analytics.get("Derogatory"))
                .violent((float) ((analytics.get("Violent") + analytics.get("Sexual")) / 2.0))
                .parent(parent)
                .build();
        evaluationRepository.save(evaluation);
        Evaluation savedEvaluation = evaluationRepository.findById(evaluation.getId()).get();
        return savedEvaluation.getId();
    }

    @Autowired
    private ChatMessageRepo chatMessageRepo;


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
        return ret;
    }

    public AnalyticsReportResponse getReport(Long id) {
        evaluationRepository.findByMemberId(id);
    }
}
