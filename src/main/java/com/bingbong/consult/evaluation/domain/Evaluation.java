package com.bingbong.consult.evaluation.domain;

import com.bingbong.consult.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member parent;
    private Float toxic; // 유해
    private Float insult; // 모욕
    private Float profanity; // 욕설
    private Float derogatory; // 경멸
    private Float violent; // 폭력 + 성적
}
