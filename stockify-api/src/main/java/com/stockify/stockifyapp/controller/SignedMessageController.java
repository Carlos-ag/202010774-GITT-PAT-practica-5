package com.stockify.stockifyapp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stockify.stockifyapp.model.SignedMessage;
import com.stockify.stockifyapp.service.SignedMessageService;



@RestController
public class SignedMessageController {

    private SignedMessageService signedMessageService;

    public SignedMessageController(SignedMessageService signedMessageService) {
        this.signedMessageService = signedMessageService;
    }

    @PostMapping("/signedMessages")
    @CrossOrigin(origins = "*")
    public void addSignedMessage(@RequestBody SignedMessage signedMessage) {
        signedMessageService.addSignedMessage(signedMessage);
    }

    @GetMapping("/signedMessages/{conversationId}")
    @CrossOrigin(origins = "*")
    public List<SignedMessage> getSignedMessages(@PathVariable("conversationId") Integer conversationId) {
        return signedMessageService.getSignedMessages(conversationId);
    }

    @GetMapping("/signedMessages/latest/{userId}")
    @CrossOrigin(origins = "*")
    public List<SignedMessage> getLatestMessagesByUserId(@PathVariable("userId") Integer userId) {


        return signedMessageService.getLatestMessagesByUserId(userId);
    }


    
    
}
