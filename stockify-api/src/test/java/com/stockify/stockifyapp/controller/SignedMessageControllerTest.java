package com.stockify.stockifyapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockify.stockifyapp.dto.SignedMessageDTO;
import com.stockify.stockifyapp.model.SignedMessage;
import com.stockify.stockifyapp.service.SignedMessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@WebMvcTest(SignedMessageController.class)
public class SignedMessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SignedMessageService signedMessageService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void addSignedMessage_shouldReturnOk_whenValidMessage() throws Exception {
        SignedMessageDTO validMessage = new SignedMessageDTO(/* your test data */);

        doNothing().when(signedMessageService).addSignedMessage(any(SignedMessageDTO.class));

        mockMvc.perform(post("/signedMessages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validMessage)))
                .andExpect(status().isOk());
    }

    @Test
    public void addSignedMessage_shouldReturnBadRequest_whenInvalidMessage() throws Exception {
        SignedMessageDTO invalidMessage = new SignedMessageDTO(/* your test data */);

        doThrow(IllegalArgumentException.class).when(signedMessageService)
                .addSignedMessage(any(SignedMessageDTO.class));

        mockMvc.perform(post("/signedMessages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidMessage)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addSignedMessage_shouldReturnInternalServerError_whenUnexpectedError() throws Exception {
        SignedMessageDTO validMessage = new SignedMessageDTO(/* your test data */);

        doThrow(RuntimeException.class).when(signedMessageService).addSignedMessage(any(SignedMessageDTO.class));

        mockMvc.perform(post("/signedMessages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validMessage)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void getLastConversationId_shouldReturnOk() throws Exception {
        Integer conversationId = 1;
        when(signedMessageService.getLastConversationId()).thenReturn(conversationId);

        mockMvc.perform(get("/lastConversationId"))
                .andExpect(status().isOk())
                .andExpect(content().string(conversationId.toString()));
    }

    @Test
    public void getSignedMessages_shouldReturnOk_whenValidConversationId() throws Exception {
        Integer validConversationId = 1;
        List<SignedMessage> messages = new ArrayList<>(); // Add test data

        when(signedMessageService.getSignedMessages(validConversationId)).thenReturn(messages);

        mockMvc.perform(get("/signedMessages/{conversationId}", validConversationId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(messages)));
    }

    @Test
    public void getLatestMessagesByUserId_shouldReturnOk_whenValidUserId() throws Exception {
        Integer validUserId = 1;
        List<SignedMessage> messages = new ArrayList<>(); // Add test data

        when(signedMessageService.getLatestMessagesByUserId(validUserId)).thenReturn(messages);

        mockMvc.perform(get("/signedMessages/latest/{userId}", validUserId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(messages)));
    }

    @Test
    public void getSignedMessages_shouldReturnBadRequest_whenInvalidConversationId() throws Exception {
        Integer invalidConversationId = -1;

        when(signedMessageService.getSignedMessages(invalidConversationId)).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(get("/signedMessages/{conversationId}", invalidConversationId))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getSignedMessages_shouldReturnInternalServerError_whenUnexpectedError() throws Exception {
        Integer validConversationId = 1;

        when(signedMessageService.getSignedMessages(validConversationId)).thenThrow(RuntimeException.class);

        mockMvc.perform(get("/signedMessages/{conversationId}", validConversationId))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void getLatestMessagesByUserId_shouldReturnBadRequest_whenInvalidUserId() throws Exception {
        Integer invalidUserId = -1;

        when(signedMessageService.getLatestMessagesByUserId(invalidUserId)).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(get("/signedMessages/latest/{userId}", invalidUserId))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getLatestMessagesByUserId_shouldReturnInternalServerError_whenUnexpectedError() throws Exception {
        Integer validUserId = 1;

        when(signedMessageService.getLatestMessagesByUserId(validUserId)).thenThrow(RuntimeException.class);

        mockMvc.perform(get("/signedMessages/latest/{userId}", validUserId))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void addSignedMessage_shouldReturnBadRequest_whenRequiredFieldMissing() throws Exception {
        SignedMessageDTO messageWithMissingField = new SignedMessageDTO();
        // faltaría el campo de userId
        messageWithMissingField.setConversationId(1);
        messageWithMissingField.setMessage("test");
    
        MvcResult result = mockMvc.perform(post("/signedMessages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(messageWithMissingField)))
                .andReturn();
    
        System.out.println("Response content: " + result.getResponse().getContentAsString());
    
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());

    }
    
}
