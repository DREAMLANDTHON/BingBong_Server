package com.bingbong.consult.evaluation.application;

import com.bingbong.consult.evaluation.domain.Evaluation;
import com.bingbong.consult.evaluation.domain.repository.EvaluationRepository;
import com.bingbong.consult.evaluation.presentation.response.AnalyticsReportResponse;
import com.bingbong.consult.member.domain.Member;
import com.bingbong.consult.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EvaluationService {
    private final EvaluationRepository evaluationRepository;
    private final MemberRepository memberRepository;
    public Long create(Long memberId, Map<String, Float> analytics){
        Member parent  = memberRepository.findById(memberId).get();
        Evaluation evaluation = Evaluation.builder()
                .toxic(analytics.get("Toxic"))
                .insult(analytics.get("Insult"))
                .profanity(analytics.get("Profanity"))
                .derogatory(analytics.get("Derogatory"))
                .violent(analytics.get("Violent") + analytics.get("Sexual"))
                .parent(parent)
                .build();
        evaluationRepository.save(evaluation);
        Evaluation savedEvaluation = evaluationRepository.findById(evaluation.getId()).get();
        return savedEvaluation.getId();
    }

//    public AnalyticsReportResponse findAllForPeriod(Long id){
//        evaluationRepository.findAllByMemberId(id);
//    }
}
