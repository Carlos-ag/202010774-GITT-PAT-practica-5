package com.stockify.stockifyapp.controller;

import org.springframework.web.bind.annotation.RestController;

import com.stockify.stockifyapp.service.UserService;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
}
