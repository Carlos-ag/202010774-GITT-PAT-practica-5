package com.stockify.stockifyapp.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ContactUsManager {
    ArrayList<UnsignedMessage> messages;
    public static String CSV_FILE = "data/contactMessages.csv";

    public ContactUsManager(ArrayList<UnsignedMessage> messages) {
        this.messages = messages;
    }

    public ContactUsManager() {
        this.messages = new ArrayList<>();
    }

    private void addMessageToCsv(UnsignedMessage message) {
        try (FileWriter fileWriter = new FileWriter(CSV_FILE, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            String messageData = String.format("\"%s\",\"%s\",\"%s\"\n", message.getName(), message.getEmail(), message.getMessage());
            bufferedWriter.write(messageData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public void addMessage(UnsignedMessage message) {
        this.messages.add(message);
        addMessageToCsv(message);
    }
}