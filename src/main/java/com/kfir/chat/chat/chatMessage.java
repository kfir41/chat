package com.kfir.chat.chat;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Builder
@Entity
public class chatMessage {

    private String message;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long messageId;
    private String Sender;
    private MessageType messageType;
}
