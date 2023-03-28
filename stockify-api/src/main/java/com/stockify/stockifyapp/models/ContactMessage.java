package com.stockify.stockifyapp.models;

import org.springframework.lang.NonNull;

public class ContactMessage {
    @NonNull
    private String name;
    @NonNull
    private String email;
    @NonNull
    private String message;

    public ContactMessage(String name, String email, String message) {
        if (name == null || email == null || message == null) {
            throw new IllegalArgumentException("Name, email, and message cannot be null");
        }
        
        this.name = name;
        this.email = email;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ContactMessage [email=" + email + ", message=" + message + ", name=" + name + "]";
    }

}
