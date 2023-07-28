package com.bingbong.consult.chatBot.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatBotRepository extends JpaRepository<ChatBot, Long> {
    List<ChatBot> findByQuestionLike(String question);
}

