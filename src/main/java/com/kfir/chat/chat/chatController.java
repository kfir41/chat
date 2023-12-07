package com.kfir.chat.chat;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@AllArgsConstructor
@RequestMapping("/messages")
public class chatController {
    @Autowired
    private final messageRepository messageRepository;
    private SimpMessagingTemplate simpMessagingTemplate;


    @MessageMapping("/chat/sendMessage")
    public void setMessage(@DestinationVariable  String to ,chatMessage message){

        boolean isExists=messageRepository.existsByReceiver(to);
        if (isExists)
        {
            messageRepository.save(message);
            simpMessagingTemplate.convertAndSend("/topic/messages"+to,message);

        }

    }


    @MessageMapping("/chat/addUser")
    @SendTo("/topic/public")
    public chatMessage addUser(
        @Payload chatMessage chatMessage,
         SimpMessageHeaderAccessor headerAccessor
        ){
            headerAccessor.getSessionAttributes().put("username",chatMessage.getSender());
            return chatMessage;
    }
}
//    @MessageMapping("/chat.sendMessage")
//    @SendTo("/topic/public")
//    public chatMessage sendMessage(
//            @Payload chatMessage chatMessage
//    ){
//        messageRepository.save(chatMessage);
//        return chatMessage;
//    }