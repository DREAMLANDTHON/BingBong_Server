package com.bingbong.consult.evaluation.presentation;

import com.bingbong.consult.evaluation.application.EvaluationService;
import com.bingbong.consult.evaluation.presentation.response.AnalyticsReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")

public class EvaluationController {
    private final EvaluationService evaluationService;

    @GetMapping("/evaluations/analytics/{id}")
    public ResponseEntity<AnalyticsReportResponse> createEvaluation(@PathVariable Long id) {
        return ResponseEntity.ok(evaluationService.getReport(id));
    }
}
