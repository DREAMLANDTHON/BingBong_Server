package com.bingbong.consult.evaluation.presentation;

import com.bingbong.consult.evaluation.application.EvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class EvaluationController {
    private final EvaluationService evaluationService;

    @PostMapping("/evaluations/{id}")
    public ResponseEntity<Long> createEvaluation(@PathVariable Long id) {
        return ResponseEntity.ok(evaluationService.create(id));
    }
}
