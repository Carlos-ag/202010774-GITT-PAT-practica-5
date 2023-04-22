package com.stockify.stockifyapp.dto;

import java.time.LocalDateTime;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

import com.stockify.stockifyapp.model.SignedMessage;
import com.stockify.stockifyapp.model.User;

public class SignedMessageDTO {
    private Integer conversationId;
    private String message;
    private Integer userId;
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
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public SignedMessage toSignedMessage() {
        SignedMessage signedMessage = new SignedMessage();
        signedMessage.setConversationId(this.getConversationId());
        signedMessage.setMessage(this.getMessage());
        signedMessage.setTimestamp(LocalDateTime.now());
        
        // Replace the User object with an AggregateReference
        AggregateReference<User, Integer> userRef = AggregateReference.to(this.getUserId());
        signedMessage.setUser(userRef);

        
        return signedMessage;
    }
  

    
}
