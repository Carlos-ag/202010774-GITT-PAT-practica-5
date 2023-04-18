package com.stockify.stockifyapp.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

public class SignedMessage {
    @Id
    private Integer id;
    private LocalDateTime timestamp;
    private Integer conversationId;
    private String message;
    @Transient
    private AggregateReference<User, Integer> user;
    
    public SignedMessage(Integer id, LocalDateTime timestamp, Integer conversationId, String message, AggregateReference<User, Integer> user) {
        this.id = id;
        this.timestamp = timestamp;
        this.conversationId = conversationId;
        this.message = message;
        this.user = user;
    }

    
}
