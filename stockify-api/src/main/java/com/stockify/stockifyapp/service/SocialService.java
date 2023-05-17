package com.stockify.stockifyapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockify.stockifyapp.model.Social;
import com.stockify.stockifyapp.repository.SocialRepository;

import java.util.List;

@Service
public class SocialService {

    @Autowired
    private SocialRepository socialRepository;

    public SocialService() {
    }

    public SocialService(SocialRepository socialRepository) {
        this.socialRepository = socialRepository;
    }

    public Iterable<Social> getAllSocial() {
        return socialRepository.findAll();
    }

    public List<Social> getUsersByName(String name) {
        return socialRepository.findByNameStartsWith(name + "%");

    }
}
