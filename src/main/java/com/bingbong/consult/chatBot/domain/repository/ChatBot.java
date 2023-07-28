package com.bingbong.consult.chatBot.domain.repository;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ChatBot {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;
    private String question;
    private String answer;
}
