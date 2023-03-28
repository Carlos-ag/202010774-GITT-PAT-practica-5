package com.stockify.stockifyapp.models;

import java.util.ArrayList;

public class ContactUsManager {
    ArrayList<ContactMessage> messages;

    public ContactUsManager(ArrayList<ContactMessage> messages) {
        this.messages = messages;
    }

    public ContactUsManager() {
        this.messages = new ArrayList<>();
    }

    public void addMessage(ContactMessage message) {
        this.messages.add(message);
    }
    

}
