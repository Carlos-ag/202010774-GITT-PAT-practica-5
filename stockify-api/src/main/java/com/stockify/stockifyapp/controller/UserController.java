package com.stockify.stockifyapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.stockify.stockifyapp.service.UserService;
import com.stockify.stockifyapp.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@RestController
public class UserController {

    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{userID}")
    @CrossOrigin(origins = "*")
    public User getUserInfo(@PathVariable("userID") Integer userID) {
        return userService.getUserInfo(userID);
    }

    @PostMapping("/users")
@CrossOrigin(origins = "*")
public ResponseEntity<User> addUser(@RequestBody User user) {
    try {
        User newUser = userService.addUser(user);
        logger.info("Added user: " + newUser.toString());
        return ResponseEntity.ok(newUser);
    } catch (IllegalArgumentException e) {
        logger.error("Error adding user: " + e.getMessage());
        return ResponseEntity.badRequest().body(null);
    } catch (Exception e) {
        logger.error("Unexpected error adding user: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}

}
