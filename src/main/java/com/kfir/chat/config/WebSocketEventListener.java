package com.kfir.chat.config;

import com.kfir.chat.chat.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {
    private final SimpMessageSendingOperations messageTemp;
    @EventListener
    public void handleWebSocketDiscnnectListener(SessionDisconnectEvent event)
    {
        StompHeaderAccessor headerAccessor=StompHeaderAccessor.wrap(event.getMessage());
        String userName=(String) headerAccessor.getSessionAttributes().get("username");
        if( userName!=null)
        {
            log.info("user disconnected: {}", userName);
            var chatMessage= com.kfir.chat.chat.chatMessage.builder()
                    .messageType(MessageType.LEAVE)
                    .Sender(userName)
                    .build();
            messageTemp.convertAndSend("/topic/public",chatMessage);
        }
    }
}
