// create the user class

package com.stockify.stockifyapp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import jakarta.persistence.Id;

public class User {
    @Id
    private UUID id;
    private String username;
    private String password;
    private String email;

    public User(UUID id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }
}