package com.stockify.stockifyapp.restservices;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.stockify.stockifyapp.models.ContactMessage;
import com.stockify.stockifyapp.models.ContactUsManager;

@Service
public class ContactMessageService {

    ContactUsManager contactUsManager;

    ContactMessageService() {
        this.contactUsManager = new ContactUsManager();
    }

    public ContactMessage addMessage(Map<String, Object> message) {
        try {
            checkIfPayloadIsValid(message);
            ContactMessage contactMessage = new ContactMessage((String) message.get("name"), (String) message.get("email"), (String) message.get("message"));
            contactUsManager.addMessage(contactMessage);
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
