package com.stockify.stockifyapp.model;

import java.time.LocalDateTime;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Id;

@Entity
@Table(name = "unsigned_messages")
public class UnsignedMessage {
    
    @Id    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "EMAIL") 
    private String email; 
    @Column(name = "MESSAGE")
    private String message;
    @Column(name = "TIMESTAMP")
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