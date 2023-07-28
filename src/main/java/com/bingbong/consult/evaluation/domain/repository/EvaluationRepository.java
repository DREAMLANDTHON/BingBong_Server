package com.bingbong.consult.evaluation.domain.repository;

import com.bingbong.consult.evaluation.domain.Evaluation;
import com.bingbong.consult.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
//    List<Evaluation> findAllByParent(Member member);

    List<Evaluation> findByParentId(Long id);
}
