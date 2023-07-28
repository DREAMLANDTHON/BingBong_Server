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

    public Long create(Long id) {
        // ChatRoomRepository에서 findById를 통해 ChatRoom을 가져온다.
        //
        // ChatMessageRepository에서 findByChatRoomId를 통해 ChatMessage들을 가져온다.
        // 존재하는지 확인하고, 존재하지 않으면 exception을 던진다.
        // 존재하면
        return 1L;
    }
}
