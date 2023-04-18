package com.stockify.stockifyapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockify.stockifyapp.model.User;
import com.stockify.stockifyapp.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User addUser(User user) {
        try {
            checkIfPayloadIsValid(user);
            User newUser = new User(user.getName(), user.getEmail(), user.getPassword());
            userRepository.save(newUser);
            return newUser;
        } catch (Exception e) {
            throw e;
        }

    }

    public void checkIfPayloadIsValid(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is required");
        }
        if (user.getName() == null) {
            throw new IllegalArgumentException("Name is required");
        }
        if (user.getEmail() == null) {
            throw new IllegalArgumentException("Email is required");
        }
        if (user.getPassword() == null) {
            throw new IllegalArgumentException("Password is required");
        }

        // check that email is valid with regex

        String email = user.getEmail();
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Email is invalid");
        }

    }
}
