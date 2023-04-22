package com.stockify.stockifyapp.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("SIGNED_MESSAGES")
public class SignedMessage {
    @Id
    private Integer id;
    @Column("TIMESTAMP")
    private LocalDateTime timestamp;
    @Column("CONVERSATION_ID")
    private Integer conversationId;
    @Column("MESSAGE")
    private String message;
    @Column("USER_ID")
    private AggregateReference<User, Integer> user;

    public SignedMessage() {
    }
    

    public SignedMessage(LocalDateTime timestamp, Integer conversationId, String message, AggregateReference<User, Integer> user) {
        this.timestamp = timestamp;
        this.conversationId = conversationId;
        this.message = message;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getConversationId() {
        return conversationId;
    }

    public void setConversationId(Integer conversationId) {
        this.conversationId = conversationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AggregateReference<User, Integer> getUser() {
        return user;
    }

    public void setUser(AggregateReference<User, Integer> user) {
        this.user = user;
    }


  

    
}
