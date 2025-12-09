package com.codeit.network.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp") // web-socket endpoint 설정
                .setAllowedOriginPatterns("*") // origins 허용 여부
                .addInterceptors(new HttpSessionHandshakeInterceptor()) // 세션기반 인증을 위한 HttpSessionHandshakeInterceptor
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메세지 브로커 생성 코드!
        // 서버 -> 클라이언트로 보내는 broker 생성, 현재 2개의 broker 생성, topic, queue
        registry.enableSimpleBroker("/topic", "/queue");

        // 클라이언트 -> 서버로 보내는 broker(중개인)
        registry.setApplicationDestinationPrefixes("/app");

    }
}
