package com.bingbong.consult.evaluation.presentation.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiveCriteria {
    Float toxic; // 유해
    Float insult; // 모욕
    Float profanity; // 욕설
    Float derogatory; // 경멸
    Float violent; // 폭력 + 성적
}
