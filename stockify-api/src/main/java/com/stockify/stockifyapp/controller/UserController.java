package com.stockify.stockifyapp.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.stockify.stockifyapp.service.UserService;
import com.stockify.stockifyapp.model.User;



@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{userID}")
    @CrossOrigin(origins = "*")
    public User getUserInfo(@PathVariable("userID") Integer userID) {

        return userService.getUserInfo(userID);
    }



}
