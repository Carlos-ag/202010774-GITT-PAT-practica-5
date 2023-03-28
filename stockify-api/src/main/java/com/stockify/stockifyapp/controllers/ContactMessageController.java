package com.stockify.stockifyapp.controllers;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stockify.stockifyapp.models.ContactMessage;
import com.stockify.stockifyapp.restservices.ContactMessageService;

@RestController
public class ContactMessageController {

    private static final Logger logger = LoggerFactory.getLogger(ContactMessageController.class);

    @Autowired
    private ContactMessageService contactMessageService;



    @PostMapping(path = "/contact", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> addContactMessage(@RequestBody Map<String, Object> message) {
        try {
            ContactMessage contactMessage = contactMessageService.addMessage(message);
            logger.info("Added contact message: " + contactMessage.toString());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error adding contact message: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
