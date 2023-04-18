package com.stockify.stockifyapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.stockify.stockifyapp.model.UnsignedMessage;

public interface UnsignedMessageRepository extends CrudRepository<UnsignedMessage, Integer>{
    
    
}
