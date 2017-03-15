package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * Created by xuyufengyy on 2017/3/13.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //订阅Broker名称
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setApplicationDestinationPrefixes("/app");
        // 应用程序以 /app 为前缀，而 代理目的地以 /topic 为前缀.
        // js.url = "/spring13/app/socket" -> @MessageMapping("/socket") 注释的方法.
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        //允许使用socketJs方式访问，访问点为socket，允许跨域
        stompEndpointRegistry.addEndpoint("/socket").setAllowedOrigins("*").withSockJS();
    }
}
