package com.stockify.stockifyapp.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("UNSIGNED_MESSAGES")
public class UnsignedMessage {
    
    @Id
    private Integer id;
    private String name;
    private String email; 
    private String message;
    private LocalDateTime timestamp;

    public UnsignedMessage() {
    }



    public UnsignedMessage(String name, String email, String message) {
        if (name == null || email == null || message == null) {
            throw new IllegalArgumentException("Name, email, and message cannot be null");
        }

        this.name = name;
        this.email = email;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ContactMessage [email=" + email + ", message=" + message + ", name=" + name + "]";
    }
}