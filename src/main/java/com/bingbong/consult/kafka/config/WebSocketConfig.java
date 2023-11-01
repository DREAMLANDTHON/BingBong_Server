package com.bingbong.consult.kafka.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // websocket에 접속하기 위한 endpoint 설정, 도메인이 다른 서버에서도 접속 가능하도록 CORS 설정

        registry.addEndpoint("/stomp/chat") // WebSocket 또는 SockJS Client가 웹소켓 핸드셰이크 커넥션을 생성할 경로
                .setAllowedOriginPatterns("*")
                .withSockJS(); // sockJS 등록
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/pub"); // client에서 SEND 요청 처리 (수신)
        registry.enableSimpleBroker("/sub"); // 해당 경로로 SimpleBroker 등록, SimpleBroker는 해당 경로를 구독하는 client에게 메시지를 전달 (발신)
    }

}