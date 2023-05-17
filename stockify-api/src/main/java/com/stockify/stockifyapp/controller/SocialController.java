package com.stockify.stockifyapp.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.stockify.stockifyapp.model.Social;
import com.stockify.stockifyapp.service.SocialService;

import java.util.List;

@RestController
public class SocialController {

    private SocialService socialService;

    public SocialController(SocialService socialService) {
        this.socialService = socialService;
    }

    @GetMapping("/socials")
    @CrossOrigin(origins = "*")
    public Iterable<Social> getAllSuscriptionPlans() {
        return socialService.getAllSocial();
    }

    @GetMapping("/socials/search")
    @CrossOrigin(origins = "*")
    public List<Social> getUsersByName(@RequestParam String name) {
        return socialService.getUsersByName(name);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleRuntimeException(RuntimeException exception) {
        return exception.getMessage();
    }
}
