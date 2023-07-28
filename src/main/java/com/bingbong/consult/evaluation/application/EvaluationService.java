package com.bingbong.consult.evaluation.application;

import com.bingbong.consult.evaluation.domain.repository.EvaluationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EvaluationService {
    private final EvaluationRepository evaluationRepository;

//    public Long createEvaluation(Long chatRoomId) {
//
//    }
}
