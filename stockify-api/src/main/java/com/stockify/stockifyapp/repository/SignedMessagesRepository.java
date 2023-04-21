package com.stockify.stockifyapp.repository;

import com.stockify.stockifyapp.model.SignedMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SignedMessagesRepository extends CrudRepository<SignedMessage, Integer> {
    List<SignedMessage> findByConversationId(Integer conversationId);
}
