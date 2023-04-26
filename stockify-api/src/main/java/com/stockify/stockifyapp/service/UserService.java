package com.stockify.stockifyapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

import com.stockify.stockifyapp.model.User;
import com.stockify.stockifyapp.model.SubscriptionPlan;
import com.stockify.stockifyapp.repository.UserRepository;
import com.stockify.stockifyapp.repository.SuscriptionPlanRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SuscriptionPlanRepository subscriptionPlanRepository;

    public UserService() {
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserInfo(Integer userId) {
        return userRepository.findById(userId).get();
    }

    public User addUser(User user, Integer subscriptionPlanId) {
        try {
            checkIfPayloadIsValid(user);
            SubscriptionPlan subscriptionPlan = subscriptionPlanRepository.findById(subscriptionPlanId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid subscription plan ID"));
            user.setSubscriptionPlan(AggregateReference.to(subscriptionPlan.getId()));
            userRepository.save(user);
            return user;
        } catch (Exception e) {
            throw e;
        }
    }

    public User updateUser(User updatedUser, Integer subscriptionPlanId) {
        checkIfPayloadIsValid(updatedUser);
        Optional<User> existingUser = userRepository.findById(updatedUser.getId());

        if (!existingUser.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }

        User userToUpdate = existingUser.get();
        userToUpdate.setName(updatedUser.getName());
        userToUpdate.setEmail(updatedUser.getEmail());
        userToUpdate.setPhone(updatedUser.getPhone());

        SubscriptionPlan subscriptionPlan = subscriptionPlanRepository.findById(subscriptionPlanId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid subscription plan ID"));
        userToUpdate.setSubscriptionPlan(AggregateReference.to(subscriptionPlan.getId()));

        return userRepository.save(userToUpdate);
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
        if (user.getPhone() == null) {
            throw new IllegalArgumentException("Password is required");
        }

        // check that email is valid with regex
        String email = user.getEmail();
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Email is invalid");
        }
    }
}
