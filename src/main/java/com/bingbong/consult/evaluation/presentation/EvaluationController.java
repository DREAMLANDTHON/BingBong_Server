package com.bingbong.consult.evaluation.presentation;

import com.bingbong.consult.evaluation.application.EvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EvaluationController {
    private final EvaluationService evaluationService;

//    @PostMapping("/evaluations/{id}")
//    public ResponseEntity<Long> createEvaluation(@PathVariable Long id) {
//        return ResponseEntity.ok(evaluationService.createEvaluation(id));
//    }
//    1. chatRoom을 chatRoomId로 받아온다.
//    2. chatRoom의 timepin을 가져온다.
//    3. timepin을 기준으로 chatRoom의 chatMessage 중 학부모 메세지를 가져와 합친다.
//    4. 합친 메세지를 기준으로 유해성 검사를 한다.

}
