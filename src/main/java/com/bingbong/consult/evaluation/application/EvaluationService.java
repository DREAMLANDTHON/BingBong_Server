package com.bingbong.consult.evaluation.application;

import com.bingbong.consult.chatmessage.domain.ChatMessage;
import com.bingbong.consult.chatmessage.domain.ChatMessageRepo;
import com.bingbong.consult.chatroom.application.ChatRoomService;
import com.bingbong.consult.chatroom.domain.ChatRoom;
import com.bingbong.consult.evaluation.domain.Evaluation;
import com.bingbong.consult.evaluation.domain.repository.EvaluationRepository;
import com.bingbong.consult.evaluation.presentation.response.AnalyticsReportResponse;
import com.bingbong.consult.evaluation.presentation.response.FiveCriteria;
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
                .violent( analytics.get("Violent"))
                .parent(parent)
                .build();
        evaluationRepository.save(evaluation);
        Evaluation savedEvaluation = evaluationRepository.findById(evaluation.getId()).get();
        return savedEvaluation.getId();
    }

    @Autowired
    private ChatMessageRepo chatMessageRepo;

    @Autowired
    private ChatRoomService chatRoomService;

    @Transactional
    public String analyse(ChatRoom chatRoom) {

        LocalDateTime start = chatRoom.getTimePin();
        chatRoomService.findChatRoom(chatRoom.getId()).update();
        LocalDateTime end = LocalDateTime.now();
        Optional<List<ChatMessage>> messages = chatMessageRepo.findByRoomTokenAndMemberIdAndSendAtGreaterThanEqualAndSendAtLessThanEqual(chatRoom.getRoomToken(), chatRoom.getParent().getId(), start, end);
        System.out.println("messages = " + messages);
        String ret="";
        ChatMessage temp;
        if(messages.isPresent()){
//            Member teacher = messages.get().get(0).getChatRoom().getClassRoom().getTeacher();
//            Member forSave=null;
            for(int i=0; i<messages.get().size();i++){
//                temp = messages.get().get(i);
//                if(temp.getMemberId() != teacher.getId() && temp.getMemberId() != 77){
                ret = ret + " " + messages.get().get(i).getMessage();
//                    forSave= temp.getMember();
//                }
            }
            System.out.println(ret);
            try {
                this.create(chatRoom.getParent().getId(), GoogleCloudTextAnalysis.analyze(ret) );

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return ret;
    }

    public AnalyticsReportResponse getReport(Long id) {
        List<Evaluation> evaluations = evaluationRepository.findByParentId(id);
        Float toxic = 0f;
        Float insult = 0f;
        Float profanity = 0f;
        Float derogatory = 0f;
        Float violent = 0f;
        for (Evaluation evaluation : evaluations) {
            toxic += evaluation.getToxic();
            insult += evaluation.getInsult();
            profanity += evaluation.getProfanity();
            derogatory += evaluation.getDerogatory();
            violent += evaluation.getViolent();
        }
        FiveCriteria average = FiveCriteria.builder()
                .toxic(toxic/evaluations.size())
                .insult(insult/evaluations.size())
                .profanity(profanity/evaluations.size())
                .derogatory(derogatory/evaluations.size())
                .violent(violent/evaluations.size())
                .build();
        return AnalyticsReportResponse.builder()
                .recentReport(average)
                .averageReport(average)
                .build();
    }
}
