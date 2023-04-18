package com.stockify.stockifyapp.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/id")
@CrossOrigin(origins = "*")
public class IDController {

    @GetMapping
    public Long getUserId() {
        return 1L; // Hardcoded userId
    }
}
