package com.stockify.stockifyapp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stockify.stockifyapp.dto.SignedMessageDTO;
import com.stockify.stockifyapp.model.SignedMessage;
import com.stockify.stockifyapp.service.SignedMessageService;

@RestController
public class SignedMessageController {

    private SignedMessageService signedMessageService;

    private static final Logger logger = LoggerFactory.getLogger(SignedMessageController.class);

    public SignedMessageController(SignedMessageService signedMessageService) {
        this.signedMessageService = signedMessageService;
    }

    @PostMapping("/signedMessages")
@CrossOrigin(origins = "*")
public ResponseEntity<Object> addSignedMessage(@RequestBody SignedMessageDTO signedMessageDTO) {
    try {
        signedMessageService.addSignedMessage(signedMessageDTO);
        logger.info("Added signed message: " + signedMessageDTO.toString());
        return ResponseEntity.ok().build();
    } catch (IllegalArgumentException e) {
        logger.error("Error adding contact message: " + e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
        logger.error("Unexpected error adding contact message: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
}


    @GetMapping("/lastConversationId")
    @CrossOrigin(origins = "*")
    public Integer getLastConversationId() {
        return signedMessageService.getLastConversationId();
    }

    @GetMapping("/signedMessages/{conversationId}")
@CrossOrigin(origins = "*")
public ResponseEntity<List<SignedMessage>> getSignedMessages(@PathVariable("conversationId") Integer conversationId) {
    try {
        List<SignedMessage> signedMessages = signedMessageService.getSignedMessages(conversationId);
        return ResponseEntity.ok(signedMessages);
    } catch (IllegalArgumentException e) {
        logger.error("Error getting signed messages: " + e.getMessage());
        return ResponseEntity.badRequest().build();
    } catch (Exception e) {
        logger.error("Unexpected error getting signed messages: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

@GetMapping("/signedMessages/latest/{userId}")
@CrossOrigin(origins = "*")
public ResponseEntity<List<SignedMessage>> getLatestMessagesByUserId(@PathVariable("userId") Integer userId) {
    try {
        List<SignedMessage> latestMessages = signedMessageService.getLatestMessagesByUserId(userId);
        return ResponseEntity.ok(latestMessages);
    } catch (IllegalArgumentException e) {
        logger.error("Error getting latest messages by user ID: " + e.getMessage());
        return ResponseEntity.badRequest().build();
    } catch (Exception e) {
        logger.error("Unexpected error getting latest messages by user ID: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}


}
