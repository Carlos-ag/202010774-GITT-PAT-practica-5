package com.stockify.stockifyapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockify.stockifyapp.dto.SignedMessageDTO;
import com.stockify.stockifyapp.model.SignedMessage;
import com.stockify.stockifyapp.repository.SignedMessagesRepository;
import com.stockify.stockifyapp.repository.SignedMessagesRepositoryImpl;

@Service
public class SignedMessageService {

    @Autowired
    private SignedMessagesRepository signedMessagesRepository;

    @Autowired
    private SignedMessagesRepositoryImpl signedMessagesRepositoryImpl;

    public void addSignedMessage(SignedMessageDTO signedMessageDTO) {
        try {
            SignedMessage signedMessage = signedMessageDTO.toSignedMessage();
            checkIfPayloadIsValid(signedMessage);
            signedMessagesRepository.save(signedMessage);
        } catch (Exception e) {
            throw e;
        }
    }


    private void checkIfPayloadIsValid(SignedMessage signedMessage) {
        if (signedMessage == null) {
            throw new IllegalArgumentException("SignedMessage is required");
        }
        if (signedMessage.getMessage() == null) {
            throw new IllegalArgumentException("Message is required");
        }
        if (signedMessage.getConversationId() == null) {
            throw new IllegalArgumentException("ConversationId is required");
        }
        if (signedMessage.getUser() == null) {
            throw new IllegalArgumentException("User is required");
        }
        if (signedMessage.getUser().getId() == null) {
            throw new IllegalArgumentException("UserId is required");
        }
    }

    public List<SignedMessage> getSignedMessages(Integer conversationId) {
        //find by tge conversation id
        return signedMessagesRepository.findByConversationId(conversationId);
    }

    public List<SignedMessage> getLatestMessagesByUserId(Integer userId) {
        return signedMessagesRepositoryImpl.findLatestMessagesByUserId(userId);
    }


    public Integer getLastConversationId() {
        return signedMessagesRepositoryImpl.findLastConversationId();
    }
    
}
