package com.stockify.stockifyapp.controller;

import com.stockify.stockifyapp.model.UnsignedMessage;
import com.stockify.stockifyapp.service.UnsignedMessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UnsignedMessageController.class)
public class UnsignedMessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UnsignedMessageService unsignedMessageService;

    private UnsignedMessage unsignedMessage;

    @BeforeEach
    public void setUp() {
        unsignedMessage = new UnsignedMessage("John Doe", "john.doe@example.com", "Test message");
    }

    // SUCCESS => este test pasa
    @Test
    public void addContactMessage_shouldAddMessage() throws Exception {
        given(unsignedMessageService.addMessage(any())).willReturn(unsignedMessage);

        mockMvc.perform(post("/contact")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Doe\",\"email\":\"john.doe@example.com\",\"message\":\"Test message\"}"))
                .andExpect(status().isOk());
    }

    // ERROR 400
    @Test
    public void addContactMessage_shouldReturnBadRequest_whenInvalidPayload() throws Exception {
        willThrow(new IllegalArgumentException("Name, email, and message cannot be null"))
            .given(unsignedMessageService).addMessage(any());

        mockMvc.perform(post("/contact")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":null,\"email\":\"john.doe@example.com\",\"message\":\"Test message\"}"))
                .andExpect(status().isBadRequest());
    }

    // ERROR 500
    @Test
    public void addContactMessage_shouldReturnInternalServerError_whenUnexpectedError() throws Exception {
        willThrow(new RuntimeException("Unexpected error"))
            .given(unsignedMessageService).addMessage(any());

        mockMvc.perform(post("/contact")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Doe\",\"email\":\"john.doe@example.com\",\"message\":\"Test message\"}"))
                .andExpect(status().isInternalServerError());
    }
}
