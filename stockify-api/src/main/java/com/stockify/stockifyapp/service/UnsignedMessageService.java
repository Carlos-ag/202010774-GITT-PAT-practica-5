package com.stockify.stockifyapp.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockify.stockifyapp.model.UnsignedMessage;
import com.stockify.stockifyapp.repository.UnsignedMessageRepository;

@Service
public class UnsignedMessageService {

    private UnsignedMessageRepository unsignedMessageRepository;

    public UnsignedMessageService(UnsignedMessageRepository unsignedMessageRepository) {
        this.unsignedMessageRepository = unsignedMessageRepository;
    }

    public UnsignedMessage addMessage(Map<String, Object> message) {
        try {
            checkIfPayloadIsValid(message);
            UnsignedMessage contactMessage = new UnsignedMessage((String) message.get("name"), (String) message.get("email"), (String) message.get("message"));
            unsignedMessageRepository.save(contactMessage);
            return contactMessage;
        }
        catch (Exception e) {
            throw e;
        }
        
        
    }

    public void checkIfPayloadIsValid(Map<String, Object> payload) {
        if(payload == null) {
            throw new IllegalArgumentException("Message is required");
        }
        if (!payload.containsKey("name")) {
            throw new IllegalArgumentException("Name is required");
        }
        if (!payload.containsKey("email")) {
            throw new IllegalArgumentException("Email is required");
        }
        if (!payload.containsKey("message")) {
            throw new IllegalArgumentException("Message is required");
        }

        // check that email is valid with regex
        
        String email = (String) payload.get("email");
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Email is invalid");
        }


    }

    
}
