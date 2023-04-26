package com.stockify.stockifyapp.controller;

import com.stockify.stockifyapp.model.User;
import com.stockify.stockifyapp.service.UserService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("John Doe", "1234567890", "john.doe@example.com", null);
        user.setId(1);
    }

    @Test
    public void getUserInfo_shouldReturnUserInfo() throws Exception {
        given(userService.getUserInfo(1)).willReturn(user);

        mockMvc.perform(get("/users/{userID}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("name").value("John Doe"))
                .andExpect(jsonPath("phone").value("1234567890"))
                .andExpect(jsonPath("email").value("john.doe@example.com"));
    }

    @Test
    public void addUser_shouldAddUser() throws Exception {
        given(userService.addUser(any(User.class), any(Integer.class))).willReturn(user);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Doe\",\"phone\":\"1234567890\",\"email\":\"john.doe@example.com\"}")
                .param("subscriptionPlanId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("name").value("John Doe"))
                .andExpect(jsonPath("phone").value("1234567890"))
                .andExpect(jsonPath("email").value("john.doe@example.com"));
    }

    @Test
    public void addUser_shouldReturnBadRequest_whenInvalidData() throws Exception {
        given(userService.addUser(any(User.class), any(Integer.class))).willThrow(new IllegalArgumentException("Invalid data"));

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Doe\",\"phone\":\"1234567890\",\"email\":\"invalid_email\"}")
                .param("subscriptionPlanId", "1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addUser_shouldReturnInternalServerError_whenUnexpectedError() throws Exception {
        given(userService.addUser(any(User.class), any(Integer.class))).willThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Doe\",\"phone\":\"1234567890\",\"email\":\"john.doe@example.com\"}")
                .param("subscriptionPlanId", "1"))
                .andExpect(status().isInternalServerError());
    }
}
